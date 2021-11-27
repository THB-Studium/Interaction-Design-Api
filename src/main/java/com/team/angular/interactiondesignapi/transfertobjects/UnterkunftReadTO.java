package com.team.angular.interactiondesignapi.transfertobjects;

import java.util.List;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.Land;

public class UnterkunftReadTO {

	private UUID id;

	private String name;

	private String link;

	private String adresse;

	private String beschreibung;

	private Land land;

	private List<byte[]> bilder;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

	public List<byte[]> getBilder() {
		return bilder;
	}

	public void setBilder(List<byte[]> bilder) {
		this.bilder = bilder;
	}

}
