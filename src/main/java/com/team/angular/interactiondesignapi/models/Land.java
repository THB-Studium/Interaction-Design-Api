package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Land {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank
    private String name;

    private byte[] bild;

    private Date startDatum;

    private Date endDatum;

    private String titel;

    private byte[] karteBild;

    private int plaetze;

    private int freiPlaetze;

    private Date anmeldungsFrist;

    //@OneToOne(fetch = FetchType.LAZY)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Erwartungen erwartungen;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Infos_land infos_Land;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Buchungsklassen buchungsklassen;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "land", fetch = FetchType.LAZY)
    private List<Highlight> highlights;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY)
	private List<Buchung> buchungen;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY)
	private List<Unterkunft> unterkunft;

}
