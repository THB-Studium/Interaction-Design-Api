package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

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

    private String titel;

    private byte[] startbild;

    private Date startDatum;

    private Date endDatum;

    private int plaetze;

    private int freiPlaetze;

    private int interessiert;
    
    private Date anmeldungsFrist;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    @OneToMany(mappedBy = "reiseAngebot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Buchungsklassen> buchungsklassen;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Erwartungen erwartungen;

    @OneToOne(fetch = FetchType.LAZY)
    private Land land;

    @OneToMany(mappedBy = "reiseAngebot", fetch = FetchType.LAZY)
    private List<Buchung> buchungen;

}
