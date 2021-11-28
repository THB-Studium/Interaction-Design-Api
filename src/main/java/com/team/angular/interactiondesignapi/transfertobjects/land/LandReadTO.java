package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.Date;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.Leistungen;

public class LandReadTO {

	private UUID id;

	private String name;

	private byte[] bild;

	private Date startDatum;

	private Date endDatum;

	private String titel;

	private byte[] karteBild;

	private int plaetze;

	private int freiPlaetze;

	private Date anmeldungsFrist;

	private Leistungen leistungen;

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

	public byte[] getBild() {
		return bild;
	}

	public void setBild(byte[] bild) {
		this.bild = bild;
	}

	public Date getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(Date startDatum) {
		this.startDatum = startDatum;
	}

	public Date getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(Date endDatum) {
		this.endDatum = endDatum;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public byte[] getKarteBild() {
		return karteBild;
	}

	public void setKarteBild(byte[] karteBild) {
		this.karteBild = karteBild;
	}

	public int getPlaetze() {
		return plaetze;
	}

	public void setPlaetze(int plaetze) {
		this.plaetze = plaetze;
	}

	public int getFreiPlaetze() {
		return freiPlaetze;
	}

	public void setFreiPlaetze(int freiPlaetze) {
		this.freiPlaetze = freiPlaetze;
	}

	public Date getAnmeldungsFrist() {
		return anmeldungsFrist;
	}

	public void setAnmeldungsFrist(Date anmeldungsFrist) {
		this.anmeldungsFrist = anmeldungsFrist;
	}

	public Leistungen getLeistungen() {
		return leistungen;
	}

	public void setLeistungen(Leistungen leistungen) {
		this.leistungen = leistungen;
	}

}
