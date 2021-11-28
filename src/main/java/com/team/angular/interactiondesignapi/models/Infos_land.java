package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Infos_land {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ElementCollection
    private List<String> abflug = new ArrayList<String>();
    @ElementCollection
    private List<String> mitreiseberechtigt = new ArrayList<String>();

    private String unterkuft_text;
    private String corona_info;
    private String klima;
    private String gesundheit;
    private String reiseordnung;
    private String hinweis;
    private String Sonstiger_hinweis;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy = "infos_Land", fetch = FetchType.LAZY)
    private Land land;

}
