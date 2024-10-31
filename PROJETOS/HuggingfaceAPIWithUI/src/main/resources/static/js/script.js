document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("imageForm");
    const statusLabel = document.getElementById("statusLabel");

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Impede o redirecionamento do formulário

        const texto = document.getElementById("texto").value; // Captura a descrição
        const diretorio = document.getElementById("diretorio").value; // Captura o diretório

        statusLabel.textContent = "Gerando imagem, aguarde..."; // Atualiza o texto do label

        // Fazendo uma requisição AJAX para o endpoint de geração de imagem
        fetch("/generate", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({ "texto": texto, "diretorio": diretorio }), // Enviando os dados
        })
            .then(response => {
                if (response.ok) {
                    return response.text(); // Retorna o texto da resposta
                }
                throw new Error('Erro na requisição');
            })
            .then(data => {
                statusLabel.textContent = data; // Atualiza o texto do label com a mensagem recebida
            })
            .catch(error => {
                statusLabel.textContent = "Erro ao gerar a imagem: " + error.message; // Atualiza o texto do label com o erro
            });
    });
});