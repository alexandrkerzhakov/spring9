package ru.gb.virusTotal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomainVotes {

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
        private String verdict;
        private long date;
        private long value;

    }
    @lombok.Data
    public static class Meta {
        private int count;
        private String cursor;
    }

    @lombok.Data
    public static class Links {
        private String self;
        private String next;
    }
}

