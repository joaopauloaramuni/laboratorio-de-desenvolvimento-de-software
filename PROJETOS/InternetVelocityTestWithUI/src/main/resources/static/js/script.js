const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/topic/speedtest', function (message) {
        const progressArea = document.getElementById('progress');
        progressArea.value += message.body + '\n';
        progressArea.scrollTop = progressArea.scrollHeight; // Auto scroll to the bottom

        // Atualizando a barra de progresso
        const progressBar = document.getElementById('progressBar');
        const messageText = message.body;

        // Verificando se a mensagem contém progresso em porcentagem
        const progressMatch = messageText.match(/(\d+(\.\d+)?)%/);
        if (progressMatch) {
            progressBar.value = parseFloat(progressMatch[1]);
        }
    });
});
