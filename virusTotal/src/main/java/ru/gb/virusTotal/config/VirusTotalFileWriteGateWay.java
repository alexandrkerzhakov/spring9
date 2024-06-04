package ru.gb.virusTotal.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "channel")
public interface VirusTotalFileWriteGateWay {
    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}
