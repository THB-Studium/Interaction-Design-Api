package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import java.util.UUID;

public class UnterkunftWriteTO {

	private UUID id;

	private String name;

	private String link;

	private String adresse;

	private String beschreibung;

	private UUID landId;

	//private List<MultipartFile> bilder;

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

	public UUID getLand() {
		return landId;
	}

	public void setLand(UUID land) {
		this.landId = land;
	}

//	public List<MultipartFile> getBilder() {
//		return bilder;
//	}
//
//	public void setBilder(List<MultipartFile> bilder) {
//		this.bilder = bilder;
//	}

}
