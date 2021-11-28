package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @OneToOne(fetch = FetchType.LAZY)
    private Leistungen leistungen;

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

    public Land() {

    }

    public Land(UUID id, @NotBlank String name, byte[] bild, Date startDatum, Date endDatum, String titel,
                byte[] karteBild, int plaetze, int freiPlaetze, Date anmeldungsFrist, Leistungen leistungen,
                List<Buchung> buchungen, List<Unterkunft> unterkunft) {

        this.id = id;
        this.name = name;
        this.bild = bild;
        this.startDatum = startDatum;
        this.endDatum = endDatum;
        this.titel = titel;
        this.karteBild = karteBild;
        this.plaetze = plaetze;
        this.freiPlaetze = freiPlaetze;
        this.anmeldungsFrist = anmeldungsFrist;
        this.leistungen = leistungen;
        this.buchungen = buchungen;
        this.unterkunft = unterkunft;
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

    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(List<Buchung> buchungen) {
        this.buchungen = buchungen;
    }

    public List<Unterkunft> getUnterkunft() {
        return unterkunft;
    }

    public void setUnterkunft(List<Unterkunft> unterkunft) {
        this.unterkunft = unterkunft;
    }

}
