package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReiseAngebot {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank
    private String titel;

    private byte[] startbild;

    private Date startDatum;

    private Date endDatum;

    @NotNull
    @Positive
    private int plaetze;

    private int freiPlaetze;

    private int interessiert;
    
    private Date anmeldungsFrist;

	private String hinweise;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> mitReiserBerechtigt;

	private String sonstigeHinweise;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    @OneToMany(mappedBy = "reiseAngebot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Buchungsklassen> buchungsklassen;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Erwartungen erwartungen;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private Land land;

    @OneToMany(mappedBy = "reiseAngebot", fetch = FetchType.LAZY)
    private List<Buchung> buchungen;

}
