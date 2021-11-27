package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Buchung {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotBlank
	private Date datum;

	private String mitReiser;

	// @NotBlank
	// private BuchungsKlasse tarif;

	@NotBlank
	private String flugAhfen;

	private String handGepaeck;

	private String koffer;

	@Enumerated(EnumType.STRING)
	@NotBlank
	private ZahlungMethod zahlungMethod;

	@NotBlank
	@ManyToOne
	@JoinColumn(name = "Reiser_id")
	private Reiser reiser;

	@NotBlank
	@ManyToOne
	@JoinColumn(name = "Land_id")
	private Land land;

	public Buchung() {

	}

	public Buchung(UUID id, @NotBlank Date datum, String mitReiser, @NotBlank String flugAhfen, String handGepaeck,
			String koffer, @NotBlank ZahlungMethod zahlungMethod, @NotBlank Reiser reiser, @NotBlank Land land) {

		this.id = id;
		this.datum = datum;
		this.mitReiser = mitReiser;
		this.flugAhfen = flugAhfen;
		this.handGepaeck = handGepaeck;
		this.koffer = koffer;
		this.zahlungMethod = zahlungMethod;
		this.reiser = reiser;
		this.land = land;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getMitReiser() {
		return mitReiser;
	}

	public void setMitReiser(String mitReiser) {
		this.mitReiser = mitReiser;
	}

	public String getFlugAhfen() {
		return flugAhfen;
	}

	public void setFlugAhfen(String flugAhfen) {
		this.flugAhfen = flugAhfen;
	}

	public String getHandGepaeck() {
		return handGepaeck;
	}

	public void setHandGepaeck(String handGepaeck) {
		this.handGepaeck = handGepaeck;
	}

	public String getKoffer() {
		return koffer;
	}

	public void setKoffer(String koffer) {
		this.koffer = koffer;
	}

	public ZahlungMethod getZahlungMethod() {
		return zahlungMethod;
	}

	public void setZahlungMethod(ZahlungMethod zahlungMethod) {
		this.zahlungMethod = zahlungMethod;
	}

	public Reiser getReiser() {
		return reiser;
	}

	public void setReiser(Reiser reiser) {
		this.reiser = reiser;
	}

	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

}
