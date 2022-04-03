package com.team.angular.interactiondesignapi.land;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.LandInfo;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reisender;
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
	
	Reisender reisender, reisender1, mitReisender;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		beschreibung.add(UUID.randomUUID().toString());
		
		abflug.add(UUID.randomUUID().toString());
		
		mitreiseberechtigt.add(UUID.randomUUID().toString());
		
		reisender = buildReisender();
		reisender = reisenderRepository.save(reisender);
		
		reisender1 = buildReisender();
		reisender1 = reisenderRepository.save(reisender1);
		
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

	
	@Test
	public void createLand() {
		LandWriteTO create = buildLandWriteTO();
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
//				.multiPart("land", create,"application/json")
//				.multiPart("bild", "something12354565".getBytes())
				.body(create)
				.log().body()
				.post("/laender")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land = landRepository.findById(id).get();
		
		assertThat(create.getName(), is(land.getName()));
		assertThat(create.getImage(), is(land.getKarte_bild()));
		assertThat(create.getFlughafen(), is(land.getFlughafen()));
		assertThat(create.getUnterkunft_text(), is(land.getUnterkunft_text()));
		
		//assertThat(create.getReiseAngebotId(), is(reiseAngebot.getId()));
	}
	
	@Test
	public void createLand__titel_exist() {
		
		LandWriteTO create = buildLandWriteTO();
		create.setName(land1.getName());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
//						.multiPart("reiseAngebot", update,"application/json")
//						.multiPart("bild", "something123".getBytes())
						.body(create)
						.log().body()
						.post("/laender")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+land1.getName()+" already exists", ex.getLocalizedMessage());
	}
	
	@Test
	public void updateLand__name_exist() {
		
		LandWriteTO update = buildLandWriteTO();
		update.setId(land.getId());
		update.setName(land1.getName());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
						.body(update)
						.log().body()
						.put("/laender")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+land1.getName()+" already exists", ex.getLocalizedMessage());
	}
	
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
			.body("id", containsInAnyOrder(land.getId().toString(), land1.getId().toString(), reiseAngebot.getLand().getId().toString(), reiseAngebot1.getLand().getId().toString()))
			.body("name", containsInAnyOrder(land.getName(), land1.getName(), reiseAngebot.getLand().getName(), reiseAngebot1.getLand().getName()));
				
	}
	
	@Test
	public void updateLand() {
		
		LandWriteTO update = buildLandWriteTO();
		update.setId(land.getId());
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
//				.multiPart("land", update,"application/json")
//				.multiPart("bild", "something123".getBytes())
				.body(update)
				.log().body()
				.put("/laender")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land_ = landRepository.findById(id).get();
		
		assertThat(update.getId(), is(land_.getId()));
		assertThat(update.getName(), is(land_.getName()));
		//assertThat(update.getImage(), is(land_.getKarte_bild()));
		assertThat(update.getFlughafen(), is(land_.getFlughafen()));
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
		assertThat(land.getFlughafen().size(), is(land_.getFlughafen().size()));
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
