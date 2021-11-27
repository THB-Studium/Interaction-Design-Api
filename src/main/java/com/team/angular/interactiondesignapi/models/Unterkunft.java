package com.team.angular.interactiondesignapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Unterkunft {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotBlank
	private String name;

	private String link;

	@NotBlank
	private String adresse;

	private String beschreibung;

	@ElementCollection
	private List<byte[]> bilder;

	@ManyToOne
	@JoinColumn(name = "Land_id")
	private Land land;

	public Unterkunft() {

	}

	public Unterkunft(UUID id, @NotBlank String name, String link, @NotBlank String adresse, String beschreibung,
			List<byte[]> bilder, Land land) {

		this.id = id;
		this.name = name;
		this.link = link;
		this.adresse = adresse;
		this.beschreibung = beschreibung;
		this.bilder = bilder;
		this.land = land;
	}

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

	public List<byte[]> getBilder() {
		return bilder;
	}

	public void setBilder(List<byte[]> bilder) {
		this.bilder = bilder;
	}

	public Land getLand() {
		return land;
	}

	public void setLand(Land land) {
		this.land = land;
	}

}
