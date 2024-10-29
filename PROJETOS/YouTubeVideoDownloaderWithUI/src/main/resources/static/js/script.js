document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("videoForm");
    const statusLabel = document.getElementById("statusLabel");

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Impede o redirecionamento do formulário

        const url = document.getElementById("url").value; // Captura a URL do campo

        // alert("Download iniciando, aguarde..."); // Alerta de início de download
        statusLabel.textContent = "Download iniciando, aguarde..."; // Atualiza o texto do label

        // Fazendo uma requisição AJAX para o endpoint de download
        fetch("/download", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({ "url": url }), // Enviando a URL
        })
            .then(response => {
                if (response.ok) {
                    return response.text(); // Retorna o texto da resposta
                }
                throw new Error('Erro na requisição');
            })
            .then(data => {
                console.log(data); // Para depuração
                // alert("Download concluído!"); // Alerta de conclusão de download
                statusLabel.textContent = "Download concluído!"; // Atualiza o texto do label
            })
            .catch(error => {
                // alert("Erro ao iniciar o download: " + error.message); // Mensagem de erro
                statusLabel.textContent = "Erro ao iniciar o download: " + error.message; // Atualiza o texto do label
            });
    });
});