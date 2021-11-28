package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.Date;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.ZahlungMethod;

public class BuchungReadTO {

	private UUID id;

	private Date datum;

	private String mitReiser;

	// @NotBlank
	// private BuchungsKlasse tarif;

	private String flugAhfen;

	private String handGepaeck;

	private String koffer;

	private ZahlungMethod zahlungMethod;

	private UUID reiserId;

	private UUID landId;
	

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

	public UUID getReiserId() {
		return reiserId;
	}

	public void setReiserId(UUID reiserId) {
		this.reiserId = reiserId;
	}

	public UUID getLandId() {
		return landId;
	}

	public void setLandId(UUID landId) {
		this.landId = landId;
	}

}
