/**
 * PDF Translator - Frontend JavaScript
 * Gerencia upload, tradução e download via AJAX
 */

(function() {
    'use strict';

    // ============================================
    // ELEMENTOS DOM
    // ============================================
    const form = document.getElementById('translateForm');
    const fileInput = document.getElementById('fileInput');
    const fileInfo = document.getElementById('fileInfo');
    const submitBtn = document.getElementById('submitBtn');
    const btnText = document.getElementById('btnText');
    const spinner = document.getElementById('spinner');
    const statusMessage = document.getElementById('statusMessage');
    const dropZone = document.getElementById('dropZone');
    const fileUploadLabel = dropZone.querySelector('.file-upload-label');

    // ============================================
    // CONFIGURAÇÃO
    // ============================================
    const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    const API_URL = '/translate';

    // ============================================
    // UTILITÁRIOS
    // ============================================

    /**
     * Mostra mensagem de status
     */
    function showStatus(message, type = 'info') {
        statusMessage.textContent = message;
        statusMessage.className = `status-message ${type} show`;
    }

    /**
     * Esconde mensagem de status
     */
    function hideStatus() {
        statusMessage.classList.remove('show');
    }

    /**
     * Ativa/desativa estado de loading
     */
    function setLoading(isLoading) {
        if (isLoading) {
            submitBtn.disabled = true;
            btnText.textContent = 'Processando...';
            spinner.classList.add('show');
            fileInput.disabled = true;
        } else {
            submitBtn.disabled = false;
            btnText.textContent = 'Traduzir e baixar TXT';
            spinner.classList.remove('show');
            fileInput.disabled = false;
        }
    }

    /**
     * Formata tamanho do arquivo
     */
    function formatFileSize(bytes) {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const sizes = ['Bytes', 'KB', 'MB', 'GB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    /**
     * Obtém nome do arquivo sem extensão
     */
    function getFileNameWithoutExtension(filename) {
        return filename.replace(/\.[^/.]+$/, '');
    }

    // ============================================
    // MANIPULAÇÃO DO ARQUIVO
    // ============================================

    /**
     * Atualiza informações do arquivo selecionado
     */
    function updateFileInfo() {
        const file = fileInput.files[0];

        if (file) {
            const fileName = file.name;
            const fileSize = formatFileSize(file.size);
            fileInfo.textContent = `${fileName} (${fileSize})`;
            fileInfo.classList.add('show');

            // Atualiza label de upload
            const labelP = fileUploadLabel.querySelector('p');
            const labelSmall = fileUploadLabel.querySelector('small');

            if (labelP) {
                labelP.textContent = fileName.length > 30
                    ? fileName.substring(0, 30) + '...'
                    : fileName;
            }
            if (labelSmall) {
                labelSmall.textContent = fileSize;
            }

            hideStatus();
        } else {
            fileInfo.classList.remove('show');

            // Restaura label original
            const labelP = fileUploadLabel.querySelector('p');
            const labelSmall = fileUploadLabel.querySelector('small');

            if (labelP) {
                labelP.textContent = 'Clique ou arraste um arquivo PDF';
            }
            if (labelSmall) {
                labelSmall.textContent = 'Limite: 10MB';
            }
        }
    }

    /**
     * Valida o arquivo selecionado
     */
    function validateFile(file) {
        if (!file) {
            showStatus('Por favor, selecione um arquivo PDF.', 'error');
            return false;
        }

        if (!file.name.toLowerCase().endsWith('.pdf')) {
            showStatus('O arquivo deve ser um PDF.', 'error');
            return false;
        }

        if (file.size > MAX_FILE_SIZE) {
            showStatus(`Arquivo muito grande! Limite: ${formatFileSize(MAX_FILE_SIZE)}`, 'error');
            return false;
        }

        if (file.size === 0) {
            showStatus('O arquivo está vazio.', 'error');
            return false;
        }

        return true;
    }

    /**
     * Reseta o formulário
     */
    function resetForm() {
        fileInput.value = '';
        updateFileInfo();
        hideStatus();
        setLoading(false);
    }

    /**
     * Processa arquivo recebido (via input ou drop)
     */
    function handleFileSelect(file) {
        if (validateFile(file)) {
            // Cria um FileList artificial para o input
            const dt = new DataTransfer();
            dt.items.add(file);
            fileInput.files = dt.files;
            updateFileInfo();
        }
    }

    // ============================================
    // DOWNLOAD DO ARQUIVO
    // ============================================

    /**
     * Faz download do blob como arquivo
     */
    function downloadBlob(blob, filename) {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }

    // ============================================
    // ENVIO DO FORMULÁRIO
    // ============================================

    /**
     * Envia o arquivo para tradução via AJAX
     */
    async function handleSubmit(event) {
        event.preventDefault();

        const file = fileInput.files[0];

        // Validação
        if (!validateFile(file)) {
            return;
        }

        // Prepara para envio
        setLoading(true);
        showStatus('🔄 Processando PDF e traduzindo... Isso pode levar alguns minutos.', 'warning');

        const formData = new FormData();
        formData.append('file', file);

        try {
            // Envia requisição
            const response = await fetch(API_URL, {
                method: 'POST',
                body: formData
            });

            // Verifica se a resposta foi bem sucedida
            if (!response.ok) {
                throw new Error(`Erro do servidor: ${response.status} ${response.statusText}`);
            }

            // Verifica o tipo de conteúdo
            const contentType = response.headers.get('content-type');

            if (contentType && contentType.includes('application/json')) {
                // Se for JSON, provavelmente é um erro
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro ao processar arquivo');
            }

            // Converte resposta para blob
            const blob = await response.blob();

            // Verifica se o blob não está vazio
            if (blob.size === 0) {
                throw new Error('Arquivo traduzido está vazio');
            }

            // Gera nome do arquivo de saída
            const originalName = getFileNameWithoutExtension(file.name);
            const outputFilename = `traducao_${originalName}.txt`;

            // Faz o download
            downloadBlob(blob, outputFilename);

            // Sucesso!
            showStatus('✅ Tradução concluída! Download iniciado.', 'success');

            // Reseta o formulário após 2 segundos
            setTimeout(() => {
                resetForm();
            }, 2000);

        } catch (error) {
            console.error('Erro na tradução:', error);

            let errorMessage = '❌ Erro ao processar o arquivo. ';

            if (error.message.includes('Failed to fetch') || error.message.includes('NetworkError')) {
                errorMessage += 'Verifique sua conexão com a internet.';
            } else if (error.message.includes('413')) {
                errorMessage += 'Arquivo muito grande para processamento.';
            } else if (error.message.includes('504')) {
                errorMessage += 'Tempo limite excedido. Tente um arquivo menor.';
            } else {
                errorMessage += error.message;
            }

            showStatus(errorMessage, 'error');
            setLoading(false);
        }
    }

    // ============================================
    // DRAG AND DROP
    // ============================================

    /**
     * Configura eventos de drag and drop
     */
    function setupDragAndDrop() {
        // Previne comportamento padrão para toda a página
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            document.addEventListener(eventName, (e) => {
                e.preventDefault();
            });
        });

        // Eventos específicos para a zona de drop
        ['dragenter', 'dragover'].forEach(eventName => {
            dropZone.addEventListener(eventName, (e) => {
                e.preventDefault();
                dropZone.classList.add('dragover');
            });
        });

        ['dragleave', 'drop'].forEach(eventName => {
            dropZone.addEventListener(eventName, (e) => {
                e.preventDefault();
                dropZone.classList.remove('dragover');
            });
        });

        // Drop - recebe o arquivo
        dropZone.addEventListener('drop', (e) => {
            e.preventDefault();
            dropZone.classList.remove('dragover');

            const files = e.dataTransfer.files;

            if (files.length > 0) {
                const file = files[0];
                handleFileSelect(file);
            }
        });

        // Click no label também funciona
        fileUploadLabel.addEventListener('click', () => {
            fileInput.click();
        });
    }

    // ============================================
    // EVENT LISTENERS
    // ============================================

    // Seleção de arquivo via input
    fileInput.addEventListener('change', (e) => {
        const file = e.target.files[0];
        if (file) {
            handleFileSelect(file);
        } else {
            updateFileInfo();
        }
    });

    // Submit do formulário
    form.addEventListener('submit', handleSubmit);

    // Configura drag and drop
    setupDragAndDrop();

    // Limpa estado inicial
    resetForm();

    console.log('✅ PDF Translator inicializado com sucesso!');
    console.log('💡 Dica: Você pode clicar ou arrastar um arquivo PDF para a área destacada.');
})();