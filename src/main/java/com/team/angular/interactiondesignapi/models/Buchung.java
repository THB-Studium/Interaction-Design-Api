package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Buchung {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate datum;

    //private Reiser mitReiser;

    private UUID mitReiserId;

    //private Buchungsklassen buchungsklasse;

    private UUID buchungsklasseId; // Tarif

    @NotBlank
    private String flughafen;

    private String handGepaeck;

    private String koffer;

    @Enumerated(EnumType.STRING)
    private ZahlungMethod zahlungMethod;

    @ManyToOne
    @JoinColumn(name = "Reiser_id")
    @EqualsAndHashCode.Exclude
    private Reiser reiser;

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private ReiseAngebot reiseAngebot;

}
