package ru.gb.kaspersky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kaspersky")
@Data
public class KasperskyProperties {
    private String BASE_URL;
    private String API_KEY;
}
