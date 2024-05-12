package ru.gb.kaspersky.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalysesFile {
    @JsonProperty("Zone")
    private String zone;

    @JsonProperty("FileGeneralInfo")
    private FileGeneralInfo fileGeneralInfo;
    @Data
    public static class FileGeneralInfo {
        @JsonProperty("FileStatus")
        private String fileStatus;

        @JsonProperty("Sha1")
        private String sha1;

        @JsonProperty("Md5")
        private String md5;

        @JsonProperty("Sha256")
        private String sha256;

        @JsonProperty("FirstSeen")
        private String firstSeen;

        @JsonProperty("LastSeen")
        private String lastSeen;

        @JsonProperty("Signer")
        private String signer;

        @JsonProperty("Packer")
        private String packer;

        @JsonProperty("Size")
        private int size;

        @JsonProperty("Type")
        private String type;

        @JsonProperty("HitsCount")
        private int hitsCount;

        @JsonProperty("DetectionsInfo")
        private List<DetectionsInfo> detectionsInfo;

        @JsonProperty("LastDetectDate")
        private String lastDetectDate;

        @JsonProperty("DescriptionUrl")
        private String descriptionUrl;

        @JsonProperty("Zone")
        private String zone;

        @JsonProperty("DetectionName")
        private String detectionName;

        @JsonProperty("DetectionMethod")
        private String detectionMethod;

        @JsonProperty("DynamicDetections")
        private List<DynamicDetections> dynamicDetections;
    }
    @Data
    public static class DetectionsInfo {
        private String zone;
        private String detectionName;
        private String detectionMethod;
    }
    @Data
    public static class DynamicDetections {
        @JsonProperty("Zone")
        private String zone;

        @JsonProperty("Threat ")
        private Long threat ;
    }
}
