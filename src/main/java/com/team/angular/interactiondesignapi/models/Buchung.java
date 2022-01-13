package com.team.angular.interactiondesignapi.models;

import java.util.Date;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Buchung {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private Date datum;

    //private Reiser mitReiser;

    private UUID mitReiserId;

    //private Buchungsklassen buchungsklasse;

    private UUID buchungsklasseId;

    @NotBlank
    private String flugHafen;

    private String handGepaeck;

    private String koffer;

    @Enumerated(EnumType.STRING)
    private ZahlungMethod zahlungMethod;

    @ManyToOne
    @JoinColumn(name = "Reiser_id")
    @EqualsAndHashCode.Exclude
    private Reiser reiser;

	@ManyToOne(fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

}
