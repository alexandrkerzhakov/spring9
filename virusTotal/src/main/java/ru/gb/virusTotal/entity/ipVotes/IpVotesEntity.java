package ru.gb.virusTotal.entity.ipVotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ipvotes")
public class IpVotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IpVotesEntityData> data;

    @OneToOne(cascade = CascadeType.ALL)
    private IpVotesEntityMeta meta;

    @OneToOne(cascade = CascadeType.ALL)
    private IpVotesEntityLinks links;
}
