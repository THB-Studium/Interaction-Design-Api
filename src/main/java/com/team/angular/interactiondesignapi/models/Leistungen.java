package com.team.angular.interactiondesignapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Leistungen {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@ElementCollection
	private List<String> beschreibung;

	public Leistungen() {
	}

	public Leistungen(UUID id, List<String> beschreibung) {
		this.id = id;
		this.beschreibung = beschreibung;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<String> getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(List<String> beschreibung) {
		this.beschreibung = beschreibung;
	}

}
