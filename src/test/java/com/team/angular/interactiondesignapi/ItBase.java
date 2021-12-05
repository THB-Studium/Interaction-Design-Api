package com.team.angular.interactiondesignapi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Leistungen;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import com.team.angular.interactiondesignapi.repositories.Infos_landRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.LeistungenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiserRepository;
import com.team.angular.interactiondesignapi.repositories.UnterkunftRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class ItBase {

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	protected MockHttpSession session;

	@Autowired
	protected FeedbackRepository feedbackRepository;

	@Autowired
	protected UnterkunftRepository unterkunftRepository;

	@Autowired
	protected LandRepository landRepository;

	@Autowired
	protected LeistungenRepository leistungenRepository;

	@Autowired
	protected ReiserRepository reiserRepository;

	@Autowired
	protected BuchungRepository buchungRepository;

	@Autowired
	protected BuchungsklassenRepository buchungsklasseRepository;

	@Autowired
	protected Infos_landRepository infos_LandRepository;

	@Autowired
	protected ErwartungenRepository erwartungenRepository;

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
		infos_LandRepository.deleteAll();
		erwartungenRepository.deleteAll();
		leistungenRepository.deleteAll();
		reiserRepository.deleteAll();
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

	protected Land buildLand() {

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
		land.setKlima(UUID.randomUUID().toString());
		land.setGesundheit(UUID.randomUUID().toString());
		land.setReiseOrdnung(UUID.randomUUID().toString());
		land.setHinweise(UUID.randomUUID().toString());
		land.setMitReiserBerechtigt(reiseBerechtig);
		land.setSonstigeHinweise(UUID.randomUUID().toString());
		// land.setReiseAngebot(land.getReiseAngebotId());

		return land;
	}

	protected LandWriteTO buildLandWriteTO(UUID erwartungenId, UUID infos_LandId, UUID buchungsklassenId) {
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
		// land.setReiseAngebot(land.getReiseAngebotId());

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

	protected Leistungen buildLeistungen(List<String> beschreibung) {
		Leistungen leistung = new Leistungen();

		leistung.setBeschreibung((beschreibung));

		return leistung;
	}

	protected Buchung buildBuchung(Reiser reiser) {
		Buchung newBuchung = new Buchung();

		newBuchung.setDatum(new Date());
		newBuchung.setMitReiserId(UUID.randomUUID());
		newBuchung.setFlugAhfen(UUID.randomUUID().toString());
		newBuchung.setHandGepaeck(UUID.randomUUID().toString());
		newBuchung.setKoffer(UUID.randomUUID().toString());
		newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
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

	protected Infos_land buildInfosLand(List<String> abflug, List<String> mitreiseberechtigt) {
		Infos_land newBuchung = new Infos_land();

		newBuchung.setTitel(UUID.randomUUID().toString());
		newBuchung.setBeschreibung(UUID.randomUUID().toString());

		return newBuchung;
	}
}
