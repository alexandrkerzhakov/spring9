package ru.gb.kaspersky.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainInfo {

    @JsonProperty("Zone")
    private String zone;

    @JsonProperty("DomainGeneralInfo")
    private DomainGeneralInfo domainGeneralInfo;

    @JsonProperty("DomainWhoIsInfo")
    private DomainWhoIsInfo domainWhoIsInfo;
    @Data
    public static class DomainGeneralInfo {
        @JsonProperty("FilesCount")
        private int filesCount;

        @JsonProperty("UrlsCount")
        private int urlsCount;

        @JsonProperty("HitsCount")
        private int hitsCount;

        @JsonProperty("Domain")
        private String domain;

        @JsonProperty("Ipv4Count")
        private int ipv4Count;

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
    public static class DomainWhoIsInfo {
        @JsonProperty("DomainName")
        private String domainName;

        @JsonProperty("Created")
        private String created;

        @JsonProperty("Expires")
        private String expires;

        @JsonProperty("NameServers")
        private List<String> nameServers;

        @JsonProperty("Contacts")
        private List<Contact> contacts;

        @JsonProperty("Registrar")
        private Registrar registrar;

        @JsonProperty("DomainStatus")
        private List<String> domainStatus;

        @JsonProperty("RegistrationOrganization")
        private String registrationOrganization;
    }
    @Data
    public static class Contact {
        @JsonProperty("ContactType")
        private String contactType;

        @JsonProperty("Organization")
        private String organization;
    }
    @Data
    public static class Registrar {
        @JsonProperty("Info")
        private String info;
    }
}