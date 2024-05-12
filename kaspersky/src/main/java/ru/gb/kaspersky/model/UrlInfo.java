package ru.gb.kaspersky.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrlInfo {

    @JsonProperty("Zone")
    private String zone;

    @JsonProperty("UrlGeneralInfo")
    private UrlGeneralInfo urlGeneralInfo;

    @JsonProperty("UrlDomainWhoIs")
    private UrlDomainWhoIs urlDomainWhoIs;
    @Data
    public static class UrlGeneralInfo {
        @JsonProperty("Url")
        private String url;

        @JsonProperty("Host")
        private String host;

        @JsonProperty("Ipv4Count")
        private int ipv4Count;

        @JsonProperty("FilesCount")
        private int filesCount;

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
    public static class UrlDomainWhoIs {
        @JsonProperty("DomainName")
        private String domainName;

        @JsonProperty("Created")
        private String created;

        @JsonProperty("Updated")
        private String updated;

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

        @JsonProperty("Name")
        private String name;

        @JsonProperty("Organization")
        private String organization;

        @JsonProperty("Address")
        private String address;

        @JsonProperty("City")
        private String city;

        @JsonProperty("State")
        private String state;

        @JsonProperty("CountryCode")
        private String countryCode;

        @JsonProperty("Phone")
        private String phone;

        @JsonProperty("Fax")
        private String fax;

        @JsonProperty("Email")
        private String email;
    }

    @Data
    public static class Registrar {
        @JsonProperty("Info")
        private String info;

        @JsonProperty("IanaId")
        private String ianaId;
    }
}

