package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reisender {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String vorname;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate geburtsdatum;

    @NotBlank
    private String telefonnummer;

    @NotBlank
    private String email;

    private String hochschule;

    @NotBlank
    private String adresse;

    private String studiengang;

    private String status;

    private String arbeitBei;

    private boolean schonTeilgenommen;

    @OneToMany(mappedBy = "reisender", fetch = FetchType.EAGER)
    private List<Buchung> buchungen;
    
    private String identity_card;

}
