package com.team.angular.interactiondesignapi.reiseangebot;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;

import io.restassured.http.ContentType;

public class ReiseAngebotIT extends ItBase {
	
	ReiseAngebot reiseAngebot, reiseAngebot1;
	
	Buchungsklassen buchungsklasse, buchungsklasse1, buchungsklasse2;
	
	ReiserWriteTO reiserWrite1;
	
	Buchung buchung, buchung1;
	
	Land land;
	
	private List<String> beschreibung = new ArrayList<>();
	
	Reiser reiser, reiser1, mitReiser;
	
	Erwartungen erwartungen;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		reiser = buildReiser();
		reiser = reiserRepository.save(reiser);
		
		reiser1 = buildReiser();
		reiser1 = reiserRepository.save(reiser1);
		
		beschreibung.add(UUID.randomUUID().toString());
		
		erwartungen = buildErwartungen();
		erwartungen = erwartungenRepository.save(erwartungen);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		buchungsklasse = buildBuchungsKlasse(land);
		buchungsklasseRepository.save(buchungsklasse);
		
		buchungsklasse1 = buildBuchungsKlasse(land);
		buchungsklasseRepository.save(buchungsklasse1);
		
		buchungsklasse2 = buildBuchungsKlasse(land);
		buchungsklasseRepository.save(buchungsklasse2);
		
		buchung = buildBuchung(reiser);
		buchung = buchungRepository.save(buchung);
		
		buchung1 = buildBuchung(reiser1);
		buchung1 = buchungRepository.save(buchung1);
		
		reiseAngebot = buildReiseAngebot();
		reiseAngebot = reiseAngebotRepository.save(reiseAngebot);
		
		reiseAngebot1 = buildReiseAngebot();
		reiseAngebot1 = reiseAngebotRepository.save(reiseAngebot1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
//	@Test
//	public void createReiseAngebot() {
//		ReiseAngebotWriteTO create = buildReiseAngebotWriteTO(reiseAngebotsklasse.getId(), land.getId());
//		
//		UUID id = UUID.fromString(
//				given()
//				.contentType(ContentType.JSON)
//				.body(create)
//				.log().body()
//				.post("/reiseAngebots")
//				.then()
//				.log().body()
//				.statusCode(200)
//				.extract().body().path("id"));
//		
//		ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id).get();
//		
//
//	}
	
	@Test
	public void listReiseAngebots() {	
		
			given()
			.contentType(ContentType.JSON)
			//.body(create)
			.log().body()
			.get("/reiseAngebote")
			.then()
			.log().body()
			.statusCode(200)
			.body("id", containsInAnyOrder(reiseAngebot.getId().toString(), reiseAngebot1.getId().toString()));
	}
	
//	@Test
//	public void updateReiseAngebot() {
//		
//		ReiseAngebotWriteTO update = buildReiseAngebotWriteTO( reiseAngebotsklasse.getId(), land.getId());
//		update.setId(reiseAngebot.getId());
//		
//		UUID id = UUID.fromString(
//				given()
//				.contentType(ContentType.JSON)
//				.body(update)
//				.log().body()
//				.put("/reiseAngebots")
//				.then()
//				.log().body()
//				.statusCode(200)
//				.extract().body().path("id"));
//		
//		ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id).get();
//		
//		assertThat(update.getId(), is(reiseAngebot.getId()));
//	}
	
//	@Test
//	public void getReiseAngebot() {
//		
//		UUID id = UUID.fromString(
//				given()
//				.contentType(ContentType.JSON)
//				//.body(reiseAngebot)
//				.log().body()
//				.get("/reiseAngebots/"+reiseAngebot.getId() )
//				.then()
//				.log().body()
//				.statusCode(200)
//				.extract().body().path("id"));
//		
//		ReiseAngebot reiseAngebot_ = reiseAngebotRepository.findById(id).get();
//		
//		assertThat(reiseAngebot.getId(), is(reiseAngebot_.getId()));
//	}
	
	@Test
	public void deleteReiseAngebot() {
		
//		reiseAngebot.setBuchungsklassen(Arrays.asList(buchungsklasse, buchungsklasse1, buchungsklasse2));
//		
//		reiseAngebot.setErwartungen(erwartungen);
//		
//		reiseAngebot.setBuchungen(Arrays.asList(buchung, buchung1));
//		
//		reiseAngebotRepository.save(reiseAngebot);

		given()
			.contentType(ContentType.JSON)
			//.body(reiseAngebot)
			.log().body()
			.delete("/reiseAngebote/"+reiseAngebot.getId())
			.then()
			.log().body()
			.statusCode(200);


//		assertThat(reiseAngebotRepository.findById(reiseAngebot.getId()).isPresent(), is(false));
//		assertThat(erwartungenRepository.findById(erwartungen.getId()).isPresent(), is(false));
//		assertThat(buchungRepository.findById(buchung.getId()).isPresent(), is(false));
//		assertThat(buchungsklasseRepository.findById(buchungsklasse.getId()).isPresent(), is(false));

	}

}
