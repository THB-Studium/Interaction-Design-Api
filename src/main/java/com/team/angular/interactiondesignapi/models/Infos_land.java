package com.team.angular.interactiondesignapi.models;

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

}
