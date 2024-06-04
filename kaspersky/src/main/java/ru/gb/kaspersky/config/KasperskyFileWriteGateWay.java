package ru.gb.kaspersky.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "channel")
public interface KasperskyFileWriteGateWay {
    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}
