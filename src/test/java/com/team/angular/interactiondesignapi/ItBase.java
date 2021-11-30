package com.team.angular.interactiondesignapi;

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
import com.team.angular.interactiondesignapi.models.Land_info;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.models.ZahlungMethod;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import com.team.angular.interactiondesignapi.repositories.Land_infoRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
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
	protected ReiserRepository reiserRepository;

	@Autowired
	protected BuchungRepository buchungRepository;

	@Autowired
	protected BuchungsklassenRepository buchungsklasseRepository;

	@Autowired
	protected Land_infoRepository infos_LandRepository;

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
		reiserRepository.deleteAll();
	}

	protected Feedback buildFeedback() {
		Feedback feedback = new Feedback();

		feedback.setAutor(UUID.randomUUID().toString());
		feedback.setDescription(UUID.randomUUID().toString());

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
		// unterkunft.setBilder(bilder);
		unterkunft.setLandId(landId);

		return unterkunft;
	}

	protected Land buildLand(Erwartungen erwartungen) {
		Land land = new Land();

		land.setName(UUID.randomUUID().toString());
		land.setBild(UUID.randomUUID().toString().getBytes());
		land.setStartDatum(new Date());
		land.setEndDatum(new Date());
		land.setTitel(UUID.randomUUID().toString());
		land.setKarteBild(UUID.randomUUID().toString().getBytes());
		land.setPlaetze(12);
		land.setFreiPlaetze(6);
		land.setAnmeldungsFrist(new Date());
		land.setErwartungen(erwartungen);

		return land;
	}

	protected LandWriteTO buildLandWriteTO(UUID erwartungenId, UUID infos_LandId, UUID buchungsklassenId) {
		LandWriteTO land = new LandWriteTO();

		land.setName(UUID.randomUUID().toString());
		land.setStartDatum(new Date());
		land.setEndDatum(new Date());
		land.setTitel(UUID.randomUUID().toString());
		land.setPlaetze(12);
		land.setFreiPlaetze(6);
		land.setAnmeldungsFrist(new Date());
		land.setErwartungenId(erwartungenId);
		land.setInfos_LandId(infos_LandId);
		land.setBuchungsklassenId(buchungsklassenId);

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

	protected ReiserWriteTO buildReiserWriteTO(List<UUID> buchungsId) {
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
		reiser.setBuchungIds(buchungsId);

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

	protected Buchung buildBuchung(Reiser reiser, Land land) {
		Buchung newBuchung = new Buchung();

		newBuchung.setDatum(new Date());
		newBuchung.setMitReiser(UUID.randomUUID().toString());
		newBuchung.setFlugAhfen(UUID.randomUUID().toString());
		newBuchung.setHandGepaeck(UUID.randomUUID().toString());
		newBuchung.setKoffer(UUID.randomUUID().toString());
		newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
		newBuchung.setReiser(reiser);
		newBuchung.setLand(land);

		return newBuchung;
	}

	protected BuchungWriteTO buildBuchungWriteTO(UUID reiserId, UUID landId, UUID buchungsklasseId) {
		BuchungWriteTO newBuchung = new BuchungWriteTO();

		newBuchung.setDatum(new Date());
		newBuchung.setBuchungsklasseId(buchungsklasseId);
		newBuchung.setMitReiser(UUID.randomUUID().toString());
		newBuchung.setFlugAhfen(UUID.randomUUID().toString());
		newBuchung.setHandGepaeck(UUID.randomUUID().toString());
		newBuchung.setKoffer(UUID.randomUUID().toString());
		newBuchung.setZahlungMethod(ZahlungMethod.Einmal);
		newBuchung.setReiserId(reiserId);
		newBuchung.setLandId(landId);

		return newBuchung;
	}

	protected Buchungsklassen buildBuchungsKlasse(Land land) {
		Buchungsklassen newBuchung = new Buchungsklassen();

		newBuchung.setType(UUID.randomUUID().toString());
		newBuchung.setPreis(12.0);
		newBuchung.setLand(land);

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

		newBuchung.setAbflug(abflug);
		newBuchung.setMitreiseberechtigt(mitreiseberechtigt);
		newBuchung.setUnterkuft_text(UUID.randomUUID().toString());
		newBuchung.setCorona_info(UUID.randomUUID().toString());
		newBuchung.setKlima(UUID.randomUUID().toString());
		newBuchung.setGesundheit(UUID.randomUUID().toString());
		newBuchung.setReiseordnung(UUID.randomUUID().toString());
		newBuchung.setSonstiger_hinweis(UUID.randomUUID().toString());

		return newBuchung;
	}
}
