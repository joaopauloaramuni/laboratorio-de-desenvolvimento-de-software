package com.example.InternetVelocityTest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

@Service
public class SpeedTestService {

    private static final String TEST_URL = "https://sampletestfile.com/wp-content/uploads/2023/07/25MB-WMV.wmv"; // URL para download e upload
    private static final int FILE_SIZE_OCTET = 25 * 1024 * 1024; // 25 MB em octetos
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedTestService.class);
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void startDownloadSpeedTest() {
        startSpeedTest(true);
    }

    public void startUploadSpeedTest() {
        startSpeedTest(false);
    }

    private void startSpeedTest(boolean isDownload) {
        final SpeedTestSocket speedTestSocket = new SpeedTestSocket();

        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {
            @Override
            public void onCompletion(SpeedTestReport report) {
                logCompletion(report, isDownload);
                // Enviando a mensagem de conclusão para o WebSocket
                String completionMessage;
                if (isDownload) {
                    completionMessage = "Download completo: " +
                            df.format(report.getTotalPacketSize() / 1_048_576.0) + " MB em " +
                            formatDuration((report.getReportTime() - report.getStartTime()) / 1_000_000_000.0) + " segundos a " +
                            df.format(report.getTransferRateBit().doubleValue() / 1_000_000) + " Mbps";
                } else {
                    completionMessage = "Upload completo: " +
                            df.format(report.getTotalPacketSize() / 1_048_576.0) + " MB em " +
                            formatDuration((report.getReportTime() - report.getStartTime()) / 1_000_000_000.0) + " segundos a " +
                            df.format(report.getTransferRateBit().doubleValue() / 1_000_000) + " Mbps";
                }
                messagingTemplate.convertAndSend("/topic/speedtest", completionMessage);
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                LOGGER.error("Erro {}: {}", speedTestError, errorMessage);
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                logProgress(report, percent, isDownload);
                // Enviando a mensagem de progresso para o WebSocket
                String progressMessage;
                if (isDownload) {
                    progressMessage = "Progresso do Download: " +
                            df.format(report.getTemporaryPacketSize() / 1_048_576.0) + " MB a " +
                            df.format(report.getTransferRateBit().doubleValue() / 1_000_000) + " Mbps - " +
                            percent + "%";
                } else {
                    progressMessage = "Progresso do Upload: " +
                            df.format(report.getTemporaryPacketSize() / 1_048_576.0) + " MB a " +
                            df.format(report.getTransferRateBit().doubleValue() / 1_000_000) + " Mbps - " +
                            percent + "%";
                }
                messagingTemplate.convertAndSend("/topic/speedtest", progressMessage);
            }
        });

        if (isDownload) {
            speedTestSocket.startDownload(TEST_URL); // Usando a mesma URL para download
        } else {
            speedTestSocket.startUpload(TEST_URL, FILE_SIZE_OCTET); // Usando a mesma URL para upload com tamanho de arquivo
        }
    }

    private void logCompletion(SpeedTestReport report, boolean isDownload) {
        double duration = (report.getReportTime() - report.getStartTime()) / 1_000_000_000.0; // Convertendo para segundos
        String formattedDuration = formatDuration(duration);
        double transferRateMbps = report.getTransferRateBit().doubleValue() / 1_000_000; // Convertendo para Mbps
        String formattedTransferRate = df.format(transferRateMbps);
        double totalPacketSizeMB = report.getTotalPacketSize() / 1_048_576.0; // Convertendo para MB
        String formattedPacketSize = df.format(totalPacketSizeMB);

        if (isDownload) {
            LOGGER.info("Download completo: {} MB em {} segundos a {} Mbps",
                    formattedPacketSize,
                    formattedDuration,
                    formattedTransferRate);
        } else {
            LOGGER.info("Upload completo: {} MB em {} segundos a {} Mbps",
                    formattedPacketSize,
                    formattedDuration,
                    formattedTransferRate);
        }
    }

    private void logProgress(SpeedTestReport report, float percent, boolean isDownload) {
        double duration = (report.getReportTime() - report.getStartTime()) / 1_000_000_000.0; // Convertendo para segundos
        String formattedDuration = formatDuration(duration);
        double transferRateMbps = report.getTransferRateBit().doubleValue() / 1_000_000; // Convertendo para Mbps
        String formattedTransferRate = df.format(transferRateMbps);
        double temporaryPacketSizeMB = report.getTemporaryPacketSize() / 1_048_576.0; // Convertendo para MB
        String formattedPacketSize = df.format(temporaryPacketSizeMB);

        if (isDownload) {
            LOGGER.info("Progresso do Download: {} MB em {} segundos a {} Mbps - {}%",
                    formattedPacketSize,
                    formattedDuration,
                    formattedTransferRate,
                    percent);
        } else {
            LOGGER.info("Progresso do Upload: {} MB em {} segundos a {} Mbps - {}%",
                    formattedPacketSize,
                    formattedDuration,
                    formattedTransferRate,
                    percent);
        }
    }

    private String formatDuration(double seconds) {
        long hours = (long) seconds / 3600;
        long minutes = (long) (seconds % 3600) / 60;
        long secs = (long) (seconds % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}

