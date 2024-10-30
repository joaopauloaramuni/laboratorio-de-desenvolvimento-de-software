document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("videoForm");
    const statusLabel = document.getElementById("statusLabel");
    const progressBar = document.getElementById("progress");
    const progressText = document.getElementById("progress-text");
    let previousProgress = 0; // Variável para armazenar o progresso anterior

    form.addEventListener("submit", function(event) {
        event.preventDefault();
        const url = document.getElementById("url").value;
        const sessionId = Math.random().toString(36).substring(2, 15);

        // Reinicia a barra de progresso
        progressBar.style.width = "0%";
        progressText.innerText = "Progresso: 0%";

        // Conectar ao endpoint de progresso via SSE
        const eventSource = new EventSource(`/progress/${sessionId}`);
        eventSource.onmessage = function(event) {
            const progress = parseFloat(event.data);
            if (progress > previousProgress) { // Verifica se o progresso aumentou
                progressBar.style.width = `${progress}%`;
                progressText.innerText = `Progresso: ${progress}%`;
                // alert("Download iniciado!);
                statusLabel.textContent = "Download iniciado!"; // Atualiza o texto do label
            }

            // Finalizar conexão SSE ao completar o download
            if (progress === 100) {
                // alert("Download concluído!"); // Alerta de conclusão de download
                statusLabel.textContent = "Download concluído!"; // Atualiza o texto do label
                setTimeout(() => {
                    eventSource.close();
                }, 1000);
            }
        };

        eventSource.onerror = function() {
            // alert("Erro ao receber atualização de progresso.");
            eventSource.close();
        };

        // alert("Download iniciando, aguarde..."); // Alerta de início de download
        statusLabel.textContent = "Download iniciando, aguarde..."; // Atualiza o texto do label

        // Iniciar o download após configurar o SSE
        fetch(`/download?url=${encodeURIComponent(url)}&sessionId=${sessionId}`, {
            method: "POST"
        }).catch(error => {
            // alert("Erro ao iniciar o download: " + error.message); // Mensagem de erro
            statusLabel.textContent = "Erro ao iniciar o download: " + error.message; // Atualiza o texto do label
        });
    });
});