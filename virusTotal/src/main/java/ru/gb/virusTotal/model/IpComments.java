package ru.gb.virusTotal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpComments {

    private List<Data> data;
    private Meta meta;
    private Links links;


    @lombok.Data
    public static class Data {
        private String id;
        private String type;
        private Links links;
        private Attributes attributes;
    }

    @lombok.Data
    public static class Attributes {
        private List<String> tags;
        private long date;
        private String text;
        private String html;
        private Votes votes;
    }

    @lombok.Data
    public static class Votes {
        private int positive;
        private int negative;
        private int abuse;
    }

    @lombok.Data
    public static class Meta {
        private int count;
    }

    @lombok.Data
    public static class Links {
        private String self;
    }
}
