package com.team.angular.interactiondesignapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reiser {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String vorname;

    @NotNull
    private Date geburtsdatum;

    @NotNull
    @Positive
    private long telefonnummer;

    @NotBlank
    private String email;

    private String hochschule;

    @NotBlank
    private String adresse;

    private String studiengang;

    private String arbeitBei;

    private boolean schonTeilgenommen;

    @OneToMany(mappedBy = "reiser", fetch = FetchType.EAGER)
    private Set<Buchung> buchungen;

}
