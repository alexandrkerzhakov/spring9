package ru.gb.virusTotal.entity.ipVotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "attributes")
public class IpVotesEntityDataAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String verdict;
    private long date;
    private long value;
}
