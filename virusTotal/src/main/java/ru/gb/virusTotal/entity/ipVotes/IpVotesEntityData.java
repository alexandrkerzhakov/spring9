package ru.gb.virusTotal.entity.ipVotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "data")
public class IpVotesEntityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private String identifier;

    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    private IpVotesEntityDataLinks links;

    @OneToOne(cascade = CascadeType.ALL)
    private IpVotesEntityDataAttributes attributes;
}
