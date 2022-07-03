package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private UUID mitReisenderId;

    // Tarif
    private UUID buchungsklasseId;

    /*best !) alternative would be to have a booking for each traveler and not for the two together*/
    private String abFlughafenReisender;
    private String abFlughafenMitReisender;

    private String ruckFlughafenReisender;
    private String ruckFlughafenMitReisender;

    private Boolean handGepaeckReisender;
    private Boolean handGepaeckMitReisender;

    private String kofferReisender;
    private String kofferMitReisender;

    @Enumerated(EnumType.STRING)
    private ZahlungMethod zahlungMethod;

    @Enumerated(EnumType.STRING)
    private Buchungstatus status;

    @ManyToOne
    @JoinColumn(name = "Reisender_id")
    private Reisender reisender;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

    // nummer to help to increment buchung
    @JsonIgnore
    private int nummer;

}
