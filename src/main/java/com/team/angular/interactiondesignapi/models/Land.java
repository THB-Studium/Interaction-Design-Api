package com.team.angular.interactiondesignapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Land {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank
    private String name;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> flughafen;

    @Lob
    private String unterkunft_text;

    private byte[] karte_bild;

    private String headerFarbe;

    private String bodyFarbe;

    @OneToOne(mappedBy = "land", fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

    @OneToMany(mappedBy = "land", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<LandInfo> landInfo;

    @OneToMany(mappedBy = "land", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Highlight> highlights;

    @OneToMany(mappedBy = "land", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Unterkunft> unterkunft;

}
