package com.team.angular.interactiondesignapi.reiser;

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
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;

import io.restassured.http.ContentType;

public class ReiserIT extends ItBase {
	
	Reiser reiser, reiser1, mitReiser;
	
	Buchung buchung, buchung1; 
	
	ReiserWriteTO reiserWrite1;
	
	Land land;
	
	ReiseAngebot reiseAngebot;
	
	private List<String> beschreibung = new ArrayList<>();
	
	Erwartungen erwartungen;
	
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
		
		erwartungen = buildErwartungen();
		erwartungen = erwartungenRepository.save(erwartungen);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		buchung = buildBuchung(reiser1);
		buchung = buchungRepository.save(buchung);
		
		buchung1 = buildBuchung(reiser1);
		buchung1 = buchungRepository.save(buchung1);
		
		reiserWrite1 = buildReiserWriteTO();
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createReiser() {
		
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(reiserWrite1)
				.log().body()
				.post("/reisers")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Reiser reiser = reiserRepository.findById(id).get();
		
		assertThat(reiserWrite1.getName(), is(reiser.getName()));
		assertThat(reiserWrite1.getVorname(), is(reiser.getVorname()));
		
	}
	
	@Test
	public void listReisers() {	
		
		given()
		.contentType(ContentType.JSON)
		.body("")
		.log().body()
		.get("/reisers")
		.then()
		.log().body()
		.statusCode(200)
		.body("id", containsInAnyOrder(reiser.getId().toString(), reiser1.getId().toString()));
					
	}
	
	@Test
	public void updateReiser() {
		
		reiserWrite1.setId(reiser.getId());
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(reiserWrite1)
				.log().body()
				.put("/reisers")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Reiser reiser_ = reiserRepository.findById(id).get();
		
		assertThat(reiserWrite1.getId(), is(reiser_.getId()));
		assertThat(reiserWrite1.getName(), is(reiser_.getName()));
		assertThat(reiserWrite1.getVorname(), is(reiser_.getVorname()));
	}
	
	@Test
	public void getReiser() {
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				//.body(reiser)
				.log().body()
				.get("/reisers/"+reiser.getId() )
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Reiser reiser_ = reiserRepository.findById(id).get();
		
		assertThat(reiser.getId(), is(reiser_.getId()));
		assertThat(reiser.getName(), is(reiser_.getName()));
		assertThat(reiser.getVorname(), is(reiser_.getVorname()));		
	}
	
	@Test
	public void deleteReiser() {
		
		reiser = buildReiser();
		reiser = reiserRepository.save(reiser);

		given()
		.contentType(ContentType.JSON)
		//.body(reiser)
		.log().body()
		.delete("/reisers/"+reiser.getId())
		.then()
		.log().body()
		.statusCode(200);

		assertThat(reiserRepository.findById(reiser.getId()).isPresent(), is(false));
	}

}
