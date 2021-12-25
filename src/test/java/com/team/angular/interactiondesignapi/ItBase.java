package com.team.angular.interactiondesignapi;

import com.team.angular.interactiondesignapi.models.*;
import com.team.angular.interactiondesignapi.repositories.*;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class ItBase {

    protected MockMvc mockMvc;
    protected MockHttpSession session;
    @Autowired
    protected FeedbackRepository feedbackRepository;
    @Autowired
    protected UnterkunftRepository unterkunftRepository;
    @Autowired
    protected LandRepository landRepository;
    @Autowired
    protected ReiserRepository reiserRepository;
    @Autowired
    protected BuchungRepository buchungRepository;
    @Autowired
    protected BuchungsklassenRepository buchungsklasseRepository;
    @Autowired
    protected LandInfoRepository landInfoRepository;
    @Autowired
    protected ErwartungenRepository erwartungenRepository;
    @Autowired
    protected ReiseAngebotRepository reiseAngebotRepository;
    @Autowired
    protected HighlightRepository highlightRepository;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.session = new MockHttpSession();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RestAssuredMockMvc.webAppContextSetup(wac);

    }

    public void cleanup() {
        feedbackRepository.deleteAll();
        buchungRepository.deleteAll();
        unterkunftRepository.deleteAll();
        landRepository.deleteAll();
        buchungsklasseRepository.deleteAll();
        landInfoRepository.deleteAll();
        erwartungenRepository.deleteAll();
        reiserRepository.deleteAll();
        reiseAngebotRepository.deleteAll();
    }

    protected Feedback buildFeedback() {
        Feedback feedback = new Feedback();

        feedback.setAutor(UUID.randomUUID().toString());
        feedback.setDescription(UUID.randomUUID().toString());
        feedback.setVeroefentlich(true);
        feedback.setBild(UUID.randomUUID().toString().getBytes());

        return feedback;
    }

    protected Unterkunft buildUnterkunft(List<byte[]> bilder, Land land) {
        Unterkunft unterkunft = new Unterkunft();

        unterkunft.setName(UUID.randomUUID().toString());
        unterkunft.setLink(UUID.randomUUID().toString());
        unterkunft.setAdresse(UUID.randomUUID().toString());
        unterkunft.setBeschreibung(UUID.randomUUID().toString());
        unterkunft.setBilder(bilder);
        unterkunft.setLand(land);

        return unterkunft;
    }

    protected UnterkunftWriteTO buildUnterkunftWriteTO(UUID landId) {
        UnterkunftWriteTO unterkunft = new UnterkunftWriteTO();

        unterkunft.setName(UUID.randomUUID().toString());
        unterkunft.setLink(UUID.randomUUID().toString());
        unterkunft.setAdresse(UUID.randomUUID().toString());
        unterkunft.setBeschreibung(UUID.randomUUID().toString());
        unterkunft.setLandId(landId);

        return unterkunft;
    }

    protected Land buildLand(ReiseAngebot reiseAngebot) {

        List<String> flug = new ArrayList<String>();
        flug.add(UUID.randomUUID().toString());
        flug.add(UUID.randomUUID().toString());

        List<String> reiseBerechtig = new ArrayList<String>();
        flug.add(UUID.randomUUID().toString());
        flug.add(UUID.randomUUID().toString());

        Land land = new Land();

        land.setName(UUID.randomUUID().toString());
        land.setFlughafen(flug);
        land.setCorona_infos(UUID.randomUUID().toString());
        land.setKarte_bild(UUID.randomUUID().toString().getBytes());
        land.setHinweise(UUID.randomUUID().toString());
        land.setMitReiserBerechtigt(reiseBerechtig);
        land.setSonstigeHinweise(UUID.randomUUID().toString());
        land.setReiseAngebot(reiseAngebot);

        return land;
    }

    protected LandWriteTO buildLandWriteTO(UUID reiseAngebotId) {
        LandWriteTO land = new LandWriteTO();

        List<String> flug = new ArrayList<String>();
        flug.add(UUID.randomUUID().toString());
        flug.add(UUID.randomUUID().toString());

        List<String> reiseBerechtig = new ArrayList<String>();
        flug.add(UUID.randomUUID().toString());
        flug.add(UUID.randomUUID().toString());

        land.setName(UUID.randomUUID().toString());
        land.setFlughafen(flug);
        land.setCorona_infos(UUID.randomUUID().toString());
        land.setKlima(UUID.randomUUID().toString());
        land.setGesundheit(UUID.randomUUID().toString());
        land.setReiseOrdnung(UUID.randomUUID().toString());
        land.setHinweise(UUID.randomUUID().toString());
        land.setMitReiserBerechtigt(reiseBerechtig);
        land.setSonstigeHinweise(UUID.randomUUID().toString());
        land.setReiseAngebotId(reiseAngebotId);

        return land;
    }

    protected ReiserWriteTO buildReiserWriteTO() {
        ReiserWriteTO reiser = new ReiserWriteTO();

        reiser.setName(UUID.randomUUID().toString());
        reiser.setVorname(UUID.randomUUID().toString());
        reiser.setGeburtsdatum(new Date());
        reiser.setTelefonnummer(1232354);
        reiser.setEmail(UUID.randomUUID().toString());
        reiser.setHochschule(UUID.randomUUID().toString());
        reiser.setAdresse(UUID.randomUUID().toString());
        reiser.setStudiengang(UUID.randomUUID().toString());
        reiser.setArbeitBei(UUID.randomUUID().toString());
        reiser.setSchonTeilgenommen(true);

        return reiser;
    }

    protected Reiser buildReiser() {
        Reiser reiser = new Reiser();

        reiser.setName(UUID.randomUUID().toString());
        reiser.setVorname(UUID.randomUUID().toString());
        reiser.setGeburtsdatum(new Date());
        reiser.setTelefonnummer(1232354);
        reiser.setEmail(UUID.randomUUID().toString());
        reiser.setHochschule(UUID.randomUUID().toString());
        reiser.setAdresse(UUID.randomUUID().toString());
        reiser.setStudiengang(UUID.randomUUID().toString());
        reiser.setArbeitBei(UUID.randomUUID().toString());
        reiser.setSchonTeilgenommen(true);

        return reiser;
    }

    protected Buchung buildBuchung(Reiser reiser) {
        Buchung newBuchung = new Buchung();

        newBuchung.setDatum(new Date());
        newBuchung.setMitReiserId(UUID.randomUUID());
        newBuchung.setFlugHafen(UUID.randomUUID().toString());
        newBuchung.setHandGepaeck(UUID.randomUUID().toString());
        newBuchung.setKoffer(UUID.randomUUID().toString());
        newBuchung.setZahlungsMethode(ZahlungMethod.Einmal);
        newBuchung.setReiser(reiser);

        return newBuchung;
    }

    protected BuchungWriteTO buildBuchungWriteTO(UUID buchungsklasseId, UUID landId) {
        BuchungWriteTO newBuchung = new BuchungWriteTO();

        newBuchung.setDatum(new Date());
        newBuchung.setBuchungsklasseId(buchungsklasseId);
        newBuchung.setMitReiser(buildReiserWriteTO());
        newBuchung.setFlugAhfen(UUID.randomUUID().toString());
        newBuchung.setHandGepaeck(UUID.randomUUID().toString());
        newBuchung.setKoffer(UUID.randomUUID().toString());
        newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
        newBuchung.setReiser(buildReiserWriteTO());

        return newBuchung;
    }

    protected Buchungsklassen buildBuchungsKlasse(Land land) {
        Buchungsklassen newBuchung = new Buchungsklassen();

        newBuchung.setType(UUID.randomUUID().toString());
        newBuchung.setPreis(12.0);

        return newBuchung;
    }

    protected Erwartungen buildErwartungen() {
        Erwartungen newErwartungen = new Erwartungen();

        newErwartungen.setAbenteuer(12);
        newErwartungen.setEntschleunigung(12);
        newErwartungen.setKonfort(12);
        newErwartungen.setNachhaltigkeit(12);
        newErwartungen.setSonne_strand(12);
        newErwartungen.setSicherheit(12);
        newErwartungen.setRoad(12);

        return newErwartungen;
    }

    protected LandInfo buildInfosLand() {
        LandInfo newBuchung = new LandInfo();

        newBuchung.setTitel(UUID.randomUUID().toString());
        newBuchung.setDescription(UUID.randomUUID().toString());

        return newBuchung;
    }
    
    protected Highlight buildHighlight(Land land) {
    	Highlight highlight = new Highlight();

    	highlight.setName(UUID.randomUUID().toString());
    	highlight.setDescription(UUID.randomUUID().toString());
    	highlight.setBild("123456".getBytes());
    	highlight.setLand(land);

        return highlight;
    }

    protected ReiseAngebot buildReiseAngebot() {

        Set<String> leistungen = new HashSet<String>();
        leistungen.add(UUID.randomUUID().toString());
        leistungen.add(UUID.randomUUID().toString());

        ReiseAngebot reiseAngebot = new ReiseAngebot();

        reiseAngebot.setTitel(UUID.randomUUID().toString());
        reiseAngebot.setStartbild("1234567890".getBytes());
        reiseAngebot.setStartDatum(new Date());
        reiseAngebot.setEndDatum(new Date());
        reiseAngebot.setPlaetze(12);
        reiseAngebot.setFreiPlaetze(12);
        reiseAngebot.setAnmeldungsFrist(new Date());
        reiseAngebot.setLeistungen(leistungen);


        return reiseAngebot;
    }
}
