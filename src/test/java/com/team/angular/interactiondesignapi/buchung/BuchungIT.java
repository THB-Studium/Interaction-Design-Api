package com.team.angular.interactiondesignapi.buchung;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

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
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;

import io.restassured.http.ContentType;

public class BuchungIT extends ItBase {
	
	Buchung buchung, buchung1, buchung2;
	
	Buchungsklassen buchungsklasse;
	
	ReiserWriteTO reiserWrite1;
	
	Land land;
	
	private List<String> beschreibung = new ArrayList<>();
	
	Reiser reiser, reiser1, mitReiser;
	
	Erwartungen erwartungen;
	
	ReiseAngebot reiseAngebot;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		reiser = buildReiser();
		reiser = reiserRepository.save(reiser);
		
		reiser1 = buildReiser();
		reiser1 = reiserRepository.save(reiser1);
		
		beschreibung.add(UUID.randomUUID().toString());
		
		reiseAngebot = buildReiseAngebot();
		reiseAngebot = reiseAngebotRepository.save(reiseAngebot);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		buchungsklasse = buildBuchungsKlasse(reiseAngebot);
		buchungsklasseRepository.save(buchungsklasse);
		
		buchung = buildBuchung(reiser, reiseAngebot);
		buchung = buchungRepository.save(buchung);
		
		buchung1 = buildBuchung(reiser1, reiseAngebot);
		buchung1 = buchungRepository.save(buchung1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createBuchung() {
		BuchungWriteTO create = buildBuchungWriteTO(buchungsklasse.getId(), land.getId(), reiseAngebot.getId());
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(create)
				.log().body()
				.post("/buchungs")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Buchung buchung = buchungRepository.findById(id).get();
		
		assertThat(create.getDatum(), is(buchung.getDatum()));
		assertThat(create.getReiseAngebotId(), is(buchung.getReiseAngebot().getId()));
		assertThat(create.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
		assertThat(create.getFlugHafen(), is(buchung.getFlugHafen()));
	}
	
	@Test
	public void listBuchungs() {	
		
			given()
			.contentType(ContentType.JSON)
			//.body(create)
			.log().body()
			.get("/buchungs")
			.then()
			.log().body()
			.statusCode(200)
			.body("id", containsInAnyOrder(buchung.getId().toString(), buchung1.getId().toString()));
				
	}
	
	/*@Test
	public void updateBuchung() {
		
		BuchungWriteTO update = buildBuchungWriteTO( buchungsklasse.getId(), land.getId(), reiseAngebot.getId());
		update.setId(buchung.getId());
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(update)
				.log().body()
				.put("/buchungs")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Buchung buchung = buchungRepository.findById(id).get();
		
		assertThat(update.getDatum(), is(buchung.getDatum()));
		assertThat(update.getReiseAngebotId(), is(buchung.getReiseAngebot().getId()));
		assertThat(update.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
		assertThat(update.getFlugHafen(), is(buchung.getFlugHafen()));	
	}*/
	
	@Test
	public void getBuchung() {
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				//.body(buchung)
				.log().body()
				.get("/buchungs/"+buchung.getId() )
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Buchung buchung_ = buchungRepository.findById(id).get();
		
		assertThat(buchung.getId(), is(buchung_.getId()));
		assertThat(buchung.getDatum(), is(buchung_.getDatum()));	
	}
	
	@Test
	public void deleteBuchung() {

		given()
		.contentType(ContentType.JSON)
		//.body(buchung)
		.log().body()
		.delete("/buchungs/"+buchung.getId())
		.then()
		.log().body()
		.statusCode(200);
	
		assertThat(buchungRepository.findById(buchung.getId()).isPresent(), is(false));
	}

}
