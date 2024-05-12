package ru.gb.virusTotal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadFile {
    private Data data;

    @lombok.Data
    public static class Data {
        private String type;
        private String id;
        private Map<String, String> links;
    }
}

