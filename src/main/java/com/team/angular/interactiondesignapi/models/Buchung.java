package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

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

    private String buchungsnummer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate buchungDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hinFlugDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ruckFlugDatum;


    private UUID mitReisenderId;

    // Tarif
    private UUID buchungsklasseId;

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
