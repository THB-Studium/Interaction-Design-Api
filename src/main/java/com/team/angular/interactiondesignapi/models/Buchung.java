package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Buchung {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate datum;

    //private Reisender mitReisender;

    private UUID mitReisenderId;

    //private Buchungsklassen buchungsklasse;

    private UUID buchungsklasseId; // Tarif

    @NotBlank
    private String flughafen;

    private String handGepaeck;

    private String koffer;

    @Enumerated(EnumType.STRING)
    private ZahlungMethod zahlungMethod;

    @Enumerated(EnumType.STRING)
    private Buchungstatus status;

    @ManyToOne
    @JoinColumn(name = "Reisender_id")
    private Reisender reisender;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

}
