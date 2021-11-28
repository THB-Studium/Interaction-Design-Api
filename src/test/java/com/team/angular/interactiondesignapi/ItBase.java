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
import org.springframework.web.multipart.MultipartFile;

import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Leistungen;
import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.LeistungenRepository;
import com.team.angular.interactiondesignapi.repositories.UnterkunftRepository;
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

	@BeforeEach
	public void setup() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RestAssuredMockMvc.webAppContextSetup(wac);

	}

	public void cleanup() {
		feedbackRepository.deleteAll();
		unterkunftRepository.deleteAll();
		landRepository.deleteAll();
		leistungenRepository.deleteAll();		
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
		//unterkunft.setBilder(bilder);
		unterkunft.setLand(landId);

		return unterkunft;
	}

	protected Land buildLand(Leistungen leistungen) {
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

		return land;
	}
	
	protected Leistungen buildLeistungen(List<String> beschreibung) {
		Leistungen leistung = new Leistungen();

		leistung.setBeschreibung((beschreibung));

		return leistung;
	}

}
