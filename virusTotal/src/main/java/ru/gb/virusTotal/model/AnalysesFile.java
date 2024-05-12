package ru.gb.virusTotal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalysesFile {
    private Data data;
    private Meta meta;


    @lombok.Data
    public static class Data {
        private String id;
        private String type;
        private Links links;
        private Attributes attributes;

        @lombok.Data
        public static class Links {
            private String self;
            private String item;
        }

        @lombok.Data
        public static class Attributes {
            private String status;
            private long date;
            private Map<String, EngineResult> results;
            private Stats stats;

            @lombok.Data
            public static class EngineResult {
                private String method;
                private String engine_name;
                private String engine_version;
                private String engine_update;
                private String category;
                private String result;
            }
        }
        @lombok.Data
        public static class Stats {
            private int malicious;
            private int suspicious;
            private int undetected;
            private int harmless;
            private int timeout;
            @JsonProperty("confirmed-timeout")
            private int confirmed_timeout;
            private int failure;
            @JsonProperty("type-unsupported")
            private int type_unsupported;
        }
    }
    @lombok.Data
    public static class Meta {
        private FileInfo file_info;

        @lombok.Data
        public static class FileInfo {
            private String sha256;
            private String md5;
            private String sha1;
            private int size;
        }
    }
}