package com.team.angular.interactiondesignapi.models;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private Buchungstatus status;

    @ManyToOne
    @JoinColumn(name = "Reisender_id")
    private Reisender reisender;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

}
