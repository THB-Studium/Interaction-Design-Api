package com.team.angular.interactiondesignapi;

import com.team.angular.interactiondesignapi.models.*;
import com.team.angular.interactiondesignapi.repositories.*;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("TestIT")
public class ItBase {

    protected MockMvc mockMvc;
    protected MockHttpSession session;
    @Autowired
    protected NewsletterRepository newsletterRepository;
    @Autowired
    protected FeedbackRepository feedbackRepository;
    @Autowired
    protected UnterkunftRepository unterkunftRepository;
    @Autowired
    protected LandRepository landRepository;
    @Autowired
    protected ReisenderRepository reisenderRepository;
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
    protected AdminRepository adminRepository;
    @Autowired
    protected PasswordEncoder bcryptEncoder;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.session = new MockHttpSession(); //todo: we don't have test with session (user)
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RestAssuredMockMvc.webAppContextSetup(wac);
    }

    public void cleanup() {
        newsletterRepository.deleteAll();
        feedbackRepository.deleteAll();
        buchungRepository.deleteAll();
        unterkunftRepository.deleteAll();
        landInfoRepository.deleteAll();
        highlightRepository.deleteAll();
        landRepository.deleteAll();
        buchungsklasseRepository.deleteAll();
        erwartungenRepository.deleteAll();
        reisenderRepository.deleteAll();
        reiseAngebotRepository.deleteAll();
        adminRepository.deleteAll();
    }

    protected Newsletter buildNewsletter() {
        Newsletter newsletter = new Newsletter();

        newsletter.setEmail(UUID.randomUUID().toString() + "@test.com");
        newsletter.setStatus(true);

        return newsletter;
    }

    protected Feedback buildFeedback() {
        Feedback feedback = new Feedback();

        feedback.setAutor(UUID.randomUUID().toString());
        feedback.setDescription(UUID.randomUUID().toString());
        feedback.setVeroeffentlich(true);
        // todo: check again test of picture
        // feedback.setBild(UUID.randomUUID().toString().getBytes());

        return feedback;
    }

    protected FeedbackWriteTO buildFeedbackWriteTO() {
        FeedbackWriteTO feedback = new FeedbackWriteTO();

        feedback.setAutor(UUID.randomUUID().toString());
        feedback.setDescription(UUID.randomUUID().toString());
        feedback.setVeroeffentlich(true);
        // todo: check again test of picture
        // feedback.setBild(UUID.randomUUID().toString());

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

        List<String> un = new ArrayList();
        // un.add("wrwer");

        UnterkunftWriteTO unterkunft = new UnterkunftWriteTO();

        unterkunft.setName(UUID.randomUUID().toString());
        unterkunft.setLink(UUID.randomUUID().toString());
        unterkunft.setAdresse(UUID.randomUUID().toString());
        unterkunft.setBeschreibung(UUID.randomUUID().toString());
        unterkunft.setLandId(landId);
        unterkunft.setBilder(un);

        return unterkunft;
    }

    protected Land buildLand(ReiseAngebot reiseAngebot) {

        List<String> flug = new ArrayList<String>();
        flug.add(UUID.randomUUID().toString());
        flug.add(UUID.randomUUID().toString());

        Land land = new Land();

        land.setName(UUID.randomUUID().toString());
        land.setFlughafen(flug);
        land.setKarte_bild(UUID.randomUUID().toString().getBytes());
        land.setReiseAngebot(reiseAngebot);

        return land;
    }

    protected LandWriteTO buildLandWriteTO() {
        LandWriteTO land = new LandWriteTO();

        List<String> flug = new ArrayList<String>();
        flug.add(UUID.randomUUID().toString());
        flug.add(UUID.randomUUID().toString());
        // land.setImage(UUID.randomUUID().toString());
        land.setName(UUID.randomUUID().toString());
        land.setFlughafen(flug);
        // land.setReiseAngebotId(reiseAngebotId);

        return land;
    }

    protected ReisenderWriteTO buildReisenderWriteTO() {
        ReisenderWriteTO reisender = new ReisenderWriteTO();

        reisender.setName(UUID.randomUUID().toString());
        reisender.setVorname(UUID.randomUUID().toString());
        reisender.setGeburtsdatum(LocalDate.now());
        reisender.setTelefonnummer("+491232354" + (int) (Math.random() * 100));
        reisender.setEmail(UUID.randomUUID().toString());
        reisender.setHochschule(UUID.randomUUID().toString());
        reisender.setAdresse(UUID.randomUUID().toString());
        reisender.setStudiengang(UUID.randomUUID().toString());
        reisender.setStatus(UUID.randomUUID().toString());
        reisender.setArbeitBei(UUID.randomUUID().toString());
        reisender.setSchonTeilgenommen(true);

        return reisender;
    }

    protected Reisender buildReisender() {
        Reisender reisender = new Reisender();

        reisender.setName(UUID.randomUUID().toString());
        reisender.setVorname(UUID.randomUUID().toString());
        reisender.setGeburtsdatum(LocalDate.now());
        reisender.setTelefonnummer("+491232354" + (int) (Math.random() * 100));
        reisender.setEmail(UUID.randomUUID().toString());
        reisender.setHochschule(UUID.randomUUID().toString());
        reisender.setAdresse(UUID.randomUUID().toString());
        reisender.setStudiengang(UUID.randomUUID().toString());
        reisender.setArbeitBei(UUID.randomUUID().toString());
        reisender.setSchonTeilgenommen(true);

        return reisender;
    }

    protected Buchung buildBuchung(Reisender reisender, ReiseAngebot ra) {
        Buchung newBuchung = new Buchung();

        newBuchung.setBuchungDatum(LocalDate.now());//todo: to extend
        newBuchung.setMitReisenderId(UUID.randomUUID());
        newBuchung.setFlughafen(UUID.randomUUID().toString());
        newBuchung.setHandGepaeck(UUID.randomUUID().toString());
        newBuchung.setKoffer(UUID.randomUUID().toString());
        newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
        newBuchung.setReisender(reisender);
        newBuchung.setReiseAngebot(ra);
        newBuchung.setStatus(Buchungstatus.Eingegangen);

        return newBuchung;
    }

    protected BuchungWriteTO buildBuchungWriteTO(UUID buchungsklasseId, UUID raId) {
        BuchungWriteTO newBuchung = new BuchungWriteTO();

        newBuchung.setBuchungDatum(LocalDate.now());//todo: to extend
        newBuchung.setBuchungsklasseId(buchungsklasseId);
        newBuchung.setMitReisender(buildReisenderWriteTO());
        newBuchung.setFlughafen(UUID.randomUUID().toString());
        newBuchung.setHandGepaeck(UUID.randomUUID().toString());
        newBuchung.setKoffer(UUID.randomUUID().toString());
        newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
        newBuchung.setReisender(buildReisenderWriteTO());
        newBuchung.setReiseAngebotId(raId);

        return newBuchung;
    }

    protected BuchungUpdateTO buildBuchungUpdateTO(UUID buchungsklasseId, UUID raId, UUID mitreisenderId, UUID reisenderId) {
        BuchungUpdateTO newBuchung = new BuchungUpdateTO();

        newBuchung.setBuchungDatum(LocalDate.now());//todo: to extend
        newBuchung.setBuchungsklasseId(buchungsklasseId);
        newBuchung.setMitReisenderId(mitreisenderId);
        newBuchung.setFlughafen(UUID.randomUUID().toString());
        newBuchung.setHandGepaeck(UUID.randomUUID().toString());
        newBuchung.setKoffer(UUID.randomUUID().toString());
        newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
        newBuchung.setReisenderId(reisenderId);
        newBuchung.setReiseAngebotId(raId);

        return newBuchung;
    }

    protected Buchungsklassen buildBuchungsKlasse(ReiseAngebot reiseAngebot) {
        Buchungsklassen newBuchung = new Buchungsklassen();

        newBuchung.setType(UUID.randomUUID().toString());
        newBuchung.setPreis(12.0);
        newBuchung.setDescription(UUID.randomUUID().toString());
        newBuchung.setReiseAngebot(reiseAngebot);

        return newBuchung;
    }

    protected BuchungsklassenReadWriteTO buildBuchungsKlasseReadWriteTO(UUID reiseAngebotId) {
        BuchungsklassenReadWriteTO newBuchung = new BuchungsklassenReadWriteTO();

        newBuchung.setType(UUID.randomUUID().toString());
        newBuchung.setPreis(12.0);
        newBuchung.setDescription(UUID.randomUUID().toString());
        newBuchung.setReiseAngebotId(reiseAngebotId);

        return newBuchung;
    }

    protected Erwartungen buildErwartungen(ReiseAngebot reiseAngebot) {
        Erwartungen newErwartungen = new Erwartungen();

        newErwartungen.setAbenteuer(12);
        newErwartungen.setEntschleunigung(12);
        newErwartungen.setKonfort(12);
        newErwartungen.setNachhaltigkeit(12);
        newErwartungen.setSonne_strand(12);
        newErwartungen.setSicherheit(12);
        newErwartungen.setRoad(12);
        newErwartungen.setReiseAngebot(reiseAngebot);

        return newErwartungen;
    }

    protected ErwartungenReadWriteTO buildErwartungenReadWriteTO(UUID reiseAngebotId) {
        ErwartungenReadWriteTO newErwartungen = new ErwartungenReadWriteTO();

        newErwartungen.setAbenteuer(12);
        newErwartungen.setEntschleunigung(12);
        newErwartungen.setKonfort(12);
        newErwartungen.setNachhaltigkeit(12);
        newErwartungen.setSonne_strand(12);
        newErwartungen.setSicherheit(12);
        newErwartungen.setRoad(12);
        newErwartungen.setReiseAngebotId(reiseAngebotId);

        return newErwartungen;
    }

    protected LandInfo buildInfosLand(Land land) {
        LandInfo newBuchung = new LandInfo();

        newBuchung.setTitel(UUID.randomUUID().toString());
        newBuchung.setDescription(UUID.randomUUID().toString());
        newBuchung.setLand(land);

        return newBuchung;
    }

    protected LandInfoReadWriteTO buildLandInfoReadWriteTO(UUID landId) {
        LandInfoReadWriteTO newBuchung = new LandInfoReadWriteTO();

        newBuchung.setTitel(UUID.randomUUID().toString());
        newBuchung.setDescription(UUID.randomUUID().toString());
        newBuchung.setLandId(landId);

        return newBuchung;
    }

    protected ReiseAngebot buildReiseAngebot() {

        List<String> mitreiseberechtigt = new ArrayList<>();
        mitreiseberechtigt.add(UUID.randomUUID().toString());
        mitreiseberechtigt.add(UUID.randomUUID().toString());

        Set<String> leistungen = new HashSet<String>();
        leistungen.add(UUID.randomUUID().toString());
        leistungen.add(UUID.randomUUID().toString());

        Land land = new Land();
        land.setName(UUID.randomUUID().toString());

        ReiseAngebot reiseAngebot = new ReiseAngebot();

        reiseAngebot.setTitel(UUID.randomUUID().toString());
        reiseAngebot.setStartbild(UUID.randomUUID().toString().getBytes());
        reiseAngebot.setStartDatum(LocalDate.now());
        reiseAngebot.setEndDatum(LocalDate.now());
        reiseAngebot.setPlaetze(12);
        reiseAngebot.setFreiPlaetze(12);
        reiseAngebot.setAnmeldungsFrist(LocalDate.now());
        reiseAngebot.setLeistungen(leistungen);
        reiseAngebot.setInteressiert(10);
        reiseAngebot.setHinweise(UUID.randomUUID().toString());
        //reiseAngebot.setMitreiseberechtigt(mitreiseberechtigt);
        reiseAngebot.setSonstigeHinweise(UUID.randomUUID().toString());
        reiseAngebot.setLand(land);

        return reiseAngebot;
    }

    protected ReiseAngebotWriteTO buildReiseAngebotWriteTO(UUID landId) {

        List<String> mitreiseberechtigt = new ArrayList<String>();
        mitreiseberechtigt.add(UUID.randomUUID().toString());
        mitreiseberechtigt.add(UUID.randomUUID().toString());

        Set<String> leistungen = new HashSet<String>();
        leistungen.add(UUID.randomUUID().toString());
        leistungen.add(UUID.randomUUID().toString());

        ReiseAngebotWriteTO reiseAngebot = new ReiseAngebotWriteTO();

        reiseAngebot.setTitel(UUID.randomUUID().toString());
        // reiseAngebot.setStartbild(UUID.randomUUID().toString());
        reiseAngebot.setStartDatum(LocalDate.now());
        reiseAngebot.setEndDatum(LocalDate.now());
        reiseAngebot.setPlaetze(12);
        reiseAngebot.setFreiPlaetze(12);
        reiseAngebot.setAnmeldungsFrist(LocalDate.now());
        reiseAngebot.setLandId(landId);
        reiseAngebot.setLeistungen(leistungen);
        reiseAngebot.setInteressiert(10);
        reiseAngebot.setHinweise(UUID.randomUUID().toString());
        //reiseAngebot.setMitreiseberechtigt(mitreiseberechtigt);
        reiseAngebot.setSonstigeHinweise(UUID.randomUUID().toString());

        return reiseAngebot;
    }

    protected Highlight buildHighlight(Land land) {
        Highlight newBuchung = new Highlight();

        newBuchung.setName(UUID.randomUUID().toString());
        newBuchung.setDescription(UUID.randomUUID().toString());
        newBuchung.setBild(UUID.randomUUID().toString().getBytes());
        newBuchung.setLand(land);

        return newBuchung;
    }

    protected HighlightReadWriteTO buildHighlightWriteTO(UUID landId) {
        HighlightReadWriteTO newBuchung = new HighlightReadWriteTO();

        newBuchung.setName(UUID.randomUUID().toString());
        newBuchung.setDescription(UUID.randomUUID().toString());
        // newBuchung.setBild(UUID.randomUUID().toString());
        newBuchung.setLandId(landId);

        return newBuchung;
    }

    protected Admin buildAdmin() {
        Admin newBuchung = new Admin();

        newBuchung.setName(UUID.randomUUID().toString());
        newBuchung.setSurname(UUID.randomUUID().toString());
        newBuchung.setPassword(UUID.randomUUID().toString());
        newBuchung.setEmail(UUID.randomUUID().toString() + "@test.com");
        newBuchung.setRole(UUID.randomUUID().toString());

        return newBuchung;
    }

    protected AdminWriteTO buildAdminWriteTO() {
        AdminWriteTO newBuchung = new AdminWriteTO();

        newBuchung.setName(UUID.randomUUID().toString());
        newBuchung.setSurname(UUID.randomUUID().toString());
        // newBuchung.setOldPassword(UUID.randomUUID().toString());
        newBuchung.setNewPassword(UUID.randomUUID().toString());
        newBuchung.setEmail(UUID.randomUUID().toString() + "@test.com");

        return newBuchung;
    }
}
