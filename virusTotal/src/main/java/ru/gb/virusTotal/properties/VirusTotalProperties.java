package ru.gb.virusTotal.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "virustotal")
@Data
public class VirusTotalProperties {
    private String BASE_URL;
    private String API_KEY;
}
