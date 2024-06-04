package ru.gb.kaspersky.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpInfo {
    @JsonProperty("Zone")
    private String zone;

    @JsonProperty("IpGeneralInfo")
    private IpGeneralInfo ipGeneralInfo;

    @JsonProperty("IpWhoIs")
    private IpWhoIs ipWhoIs;

    @Data
    public static class IpGeneralInfo {
        @JsonProperty("Status")
        private String status;

        @JsonProperty("CountryCode")
        private String countryCode;

        @JsonProperty("HitsCount")
        private Long hitsCount;

        @JsonProperty("FirstSeen")
        private String firstSeen;

        @JsonProperty("Ip")
        private String ip;

        @JsonProperty("Categories")
        private List<String> categories;

        @JsonProperty("CategoriesWithZone")
        private List<CategoryWithZone> categoriesWithZone;
    }
    @Data
    public static class CategoryWithZone {
        @JsonProperty("Name")
        private String name;

        @JsonProperty("Zone")
        private String zone;
    }
    @Data
    public static class IpWhoIs {
        @JsonProperty("Asn")
        private List<Asn> asn;

        @JsonProperty("Net")
        private Net net;
    }
    @Data
    public static class Asn {
        @JsonProperty("Number")
        private int number;

        @JsonProperty("Description")
        private List<String> description;
    }
    @Data
    public static class Net {
        @JsonProperty("RangeStart")
        private String rangeStart;

        @JsonProperty("RangeEnd")
        private String rangeEnd;

        @JsonProperty("Created")
        private String created;

        @JsonProperty("Changed")
        private String changed;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Description")
        private String description;
    }
}

