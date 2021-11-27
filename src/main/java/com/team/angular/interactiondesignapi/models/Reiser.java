package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Reiser {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private String vorname;

	@NotBlank
	private Date geburtsdatung;

	private long telefonnummer;

	@NotBlank
	private String email;

	private String hochschule;

	@NotBlank
	private String adresse;

	private String studiengang;

	private String arbeitBei;

	private boolean schonTeilgenommen;

	@OneToMany(mappedBy = "reiser")
	private List<Buchung> buchungen;

	public Reiser() {

	}

	public Reiser(UUID id, String name, String vorname, Date geburtsdatung, long telefonnummer, String email,
			String hochschule, String adresse, String studiengang, String arbeitBei, boolean schonTeilgenommen,
			List<Buchung> buchungen) {

		this.id = id;
		this.name = name;
		this.vorname = vorname;
		this.geburtsdatung = geburtsdatung;
		this.telefonnummer = telefonnummer;
		this.email = email;
		this.hochschule = hochschule;
		this.adresse = adresse;
		this.studiengang = studiengang;
		this.arbeitBei = arbeitBei;
		this.schonTeilgenommen = schonTeilgenommen;
		this.buchungen = buchungen;
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

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Date getGeburtsdatung() {
		return geburtsdatung;
	}

	public void setGeburtsdatung(Date geburtsdatung) {
		this.geburtsdatung = geburtsdatung;
	}

	public long getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(long telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHochschule() {
		return hochschule;
	}

	public void setHochschule(String hochschule) {
		this.hochschule = hochschule;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getStudiengang() {
		return studiengang;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	public String getArbeitBei() {
		return arbeitBei;
	}

	public void setArbeitBei(String arbeitBei) {
		this.arbeitBei = arbeitBei;
	}

	public boolean isSchonTeilgenommen() {
		return schonTeilgenommen;
	}

	public void setSchonTeilgenommen(boolean schonTeilgenommen) {
		this.schonTeilgenommen = schonTeilgenommen;
	}

	public List<Buchung> getBuchungen() {
		return buchungen;
	}

	public void setBuchungen(List<Buchung> buchungen) {
		this.buchungen = buchungen;
	}

}
