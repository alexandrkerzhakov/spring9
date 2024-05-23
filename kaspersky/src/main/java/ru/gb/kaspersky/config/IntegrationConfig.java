package ru.gb.kaspersky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class IntegrationConfig {
    private final File ipInfoFile = new File(Paths.get("").toAbsolutePath() + "\\kaspersky\\ipLog");

    @Bean
    public MessageChannel channel(){
        return new DirectChannel();
    }


    @Bean
    @ServiceActivator(inputChannel = "channel")
    public FileWritingMessageHandler myHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(ipInfoFile);
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}

