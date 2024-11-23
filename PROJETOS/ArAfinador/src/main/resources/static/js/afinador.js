function startListening() {
    navigator.mediaDevices.getUserMedia({ audio: true })
        .then(function(stream) {
            const audioContext = new (window.AudioContext || window.webkitAudioContext)();
            const analyser = audioContext.createAnalyser();
            const microphone = audioContext.createMediaStreamSource(stream);
            microphone.connect(analyser);
            analyser.fftSize = 32768;
            const bufferLength = analyser.frequencyBinCount;
            const dataArray = new Uint8Array(bufferLength);
            //let lastUpdateTime = 0;

            function detectFrequency() {
                //const currentTime = Date.now();
                //if (currentTime - lastUpdateTime >= 100) { // Atualiza a cada 100 ms
                //lastUpdateTime = currentTime;
                analyser.getByteFrequencyData(dataArray);

                let maxIndex = 0;
                let maxValue = 0;

                // Ajustando o índice inicial para ignorar frequências muito baixas
                const startIndex = Math.floor(20 * bufferLength / (audioContext.sampleRate / 2));

                for (let i = startIndex; i < bufferLength; i++) {
                    if (dataArray[i] > maxValue) {
                        maxValue = dataArray[i];
                        maxIndex = i;
                    }
                }

                if (maxValue > 50) {  // Só processa se o som for forte o suficiente
                    const nyquist = audioContext.sampleRate / 2;
                    const rawFrequency = maxIndex * nyquist / bufferLength;
                    const nota = getNota(rawFrequency);
                    document.getElementById('nota').innerText = nota.nome;

                    // Exibe a frequência base
                    document.getElementById('frequencia').innerText = 'Frequência base: ' + rawFrequency.toFixed(2) + ' Hz';

                    // Atualiza a barra de frequência
                    updateFrequencyBar(rawFrequency, nota);

                    const notaElement = document.getElementById('nota');
                    if (nota.afinado) {
                        notaElement.classList.add('afinado');
                        notaElement.classList.remove('desafinada');
                    } else {
                        notaElement.classList.add('desafinada');
                        notaElement.classList.remove('afinado');
                    }
                }
                //}
                requestAnimationFrame(detectFrequency);
            }

            detectFrequency();
        })
        .catch(function(err) {
            console.error('Erro ao acessar o microfone:', err);
        });
}

function getNota(frequency) {
    const notas = {
        55.00: "A", 58.27: "A#", 61.74: "B", 65.41: "C",
        69.30: "C#", 73.42: "D", 77.78: "D#", 82.41: "E",
        87.31: "F", 92.50: "F#", 98.00: "G", 103.83: "G#",
        110.00: "A", 116.54: "A#", 123.47: "B", 130.81: "C",
        138.59: "C#", 146.83: "D", 155.56: "D#", 164.81: "E",
        174.61: "F", 185.00: "F#", 196.00: "G", 207.65: "G#",
        220.00: "A", 233.08: "A#", 246.94: "B", 261.63: "C",
        277.18: "C#", 293.66: "D", 311.13: "D#", 329.63: "E",
        349.23: "F", 369.99: "F#", 392.00: "G", 415.30: "G#"
    };

    if (frequency >= 440) {
        return { nome: "> 440Hz", afinado: false };
    }

    const toleranceCents = 10; // Define a tolerância de afinação em cents, ajuste conforme necessário

    let notaMaisProxima = { nome: "Nota não detectada", afinado: false };
    let menorDiferenca = Number.MAX_VALUE;

    for (const freq in notas) {
        const freqNum = parseFloat(freq);

        // Calcula a diferença em cents entre a frequência analisada e a frequência base
        const cents = 1200 * Math.log2(frequency / freqNum);
        const diff = Math.abs(cents);

        // Exibe as informações de cada comparação
        // console.log(`Freq analisada: ${freqNum} Hz, Nota: ${notas[freq]}`);
        // console.log(`Frequência: ${frequency} Hz, Diferença em cents: ${cents.toFixed(2)}, Diferença absoluta: ${diff.toFixed(2)}`);

        if (diff < menorDiferenca) {
            menorDiferenca = diff;
            notaMaisProxima = {
                nome: notas[freq],
                afinado: diff < toleranceCents
            };
        }
    }

    // console.log(`Nota mais próxima: ${notaMaisProxima.nome}, Afinado: ${notaMaisProxima.afinado}`);
    return notaMaisProxima;
}

function updateFrequencyBar(frequency, nota) {
    const bar = document.getElementById('frequencia-bar');

    // Normaliza a frequência para um valor entre 0 e 100
    const normalizedFrequency = Math.min(Math.max(frequency / 440, 0), 1) * 100; // Frequência normalizada, usando o A4 (440Hz) como referência

    // Atualiza a largura da barra
    bar.style.width = normalizedFrequency + '%';

    // Muda a cor da barra conforme a nota afinada ou desafinada
    if (nota.afinado) {
        bar.classList.add('afinado-bar');
        bar.classList.remove('desafinada-bar');
    } else {
        bar.classList.add('desafinada-bar');
        bar.classList.remove('afinado-bar');
    }
}