package com.team.angular.interactiondesignapi.land;

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
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.LandInfo;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;

import io.restassured.http.ContentType;

public class LandIT extends ItBase {
	
	Land land, land1, land2;
	
	Buchungsklassen buchungsklasse;
	
	Erwartungen erwartungen;
	
	LandInfo infos_land;
	
	LandInfo leistungen;
	
	ReiseAngebot reiseAngebot, reiseAngebot1;
	
	private List<String> beschreibung = new ArrayList<>();
	
	private List<String> abflug = new ArrayList<>();
	
	private List<String> mitreiseberechtigt = new ArrayList<>();
	
	Reiser reiser, reiser1, mitReiser;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		beschreibung.add(UUID.randomUUID().toString());
		
		abflug.add(UUID.randomUUID().toString());
		
		mitreiseberechtigt.add(UUID.randomUUID().toString());
		
		reiser = buildReiser();
		reiser = reiserRepository.save(reiser);
		
		reiser1 = buildReiser();
		reiser1 = reiserRepository.save(reiser1);
		
		reiseAngebot = buildReiseAngebot();
		reiseAngebot = reiseAngebotRepository.save(reiseAngebot);
		
		reiseAngebot1 = buildReiseAngebot();
		reiseAngebot1 = reiseAngebotRepository.save(reiseAngebot1);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		buchungsklasse = buildBuchungsKlasse(reiseAngebot);
		buchungsklasseRepository.save(buchungsklasse);
		
		land1 = buildLand(reiseAngebot);
		land1 = landRepository.save(land1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	/*@Test
	public void createLand() {
		LandWriteTO create = buildLandWriteTO(reiseAngebot.getId());
		
		UUID id = UUID.fromString(
				given()
				//.contentType(ContentType.JSON)
				.multiPart("land", create,"application/json")
				.multiPart("bild", "something12354565".getBytes())
				//.body(create)
				.log().body()
				.post("/laender")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land = landRepository.findById(id).get();
		
		assertThat(create.getName(), is(land.getName()));
		//assertThat("something12354565".getBytes(), is(land.getKarte_bild()));
		assertThat(create.getFlughafen(), is(land.getFlughafen()));
		assertThat(create.getUnterkunft_text(), is(land.getUnterkunft_text()));
		
		//assertThat(create.getReiseAngebotId(), is(reiseAngebot.getId()));
	}*/
	
	@Test
	public void listLands() {	
		
			given()
			.contentType(ContentType.JSON)
			//.body(create)
			.log().body()
			.get("/laender")
			.then()
			.log().body()
			.statusCode(200)
			.body("id", containsInAnyOrder(land.getId().toString(), land1.getId().toString()))
			.body("name", containsInAnyOrder(land.getName(), land1.getName()));
				
	}
	
	@Test
	public void updateLand() {
		
		LandWriteTO update = buildLandWriteTO(reiseAngebot1.getId());
		update.setId(land.getId());
		
		UUID id = UUID.fromString(
				given()
				//.contentType(ContentType.JSON)
				.multiPart("land", update,"application/json")
				.multiPart("bild", "something123".getBytes())
				//.body(create)
				.log().body()
				.put("/laender")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land_ = landRepository.findById(id).get();
		
		assertThat(update.getId(), is(land_.getId()));
		assertThat(update.getName(), is(land_.getName()));
		//assertThat("something12354565".getBytes(), is(land_.getKarte_bild()));
		assertThat(update.getFlugHafen(), is(land_.getFlugHafen()));
		assertThat(update.getUnterkunft_text(), is(land_.getUnterkunft_text()));
		
		//assertThat(update.getReiseAngebotId(), is(reiseAngebot1.getId()));
	}
	
	@Test
	public void getLand() {
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				//.body(land)
				.log().body()
				.get("/laender/"+land.getId() )
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land_ = landRepository.findById(id).get();
		
		assertThat(land.getId(), is(land_.getId()));
		assertThat(land.getId(), is(land_.getId()));
		assertThat(land.getName(), is(land_.getName()));
		assertThat(land.getName(), is(land_.getName()));
		//assertThat("something12354565".getBytes(), is(land_.getKarte_bild()));
		assertThat(land.getFlugHafen().size(), is(land_.getFlugHafen().size()));
		assertThat(land.getUnterkunft_text(), is(land_.getUnterkunft_text()));
	}
	
	@Test
	public void deleteLand() {

		given()
		.contentType(ContentType.JSON)
		//.body(land)
		.log().body()
		.delete("/laender/"+land.getId())
		.then()
		.log().body()
		.statusCode(200);
	
		assertThat(landRepository.findById(land.getId()).isPresent(), is(false));
	}

}
