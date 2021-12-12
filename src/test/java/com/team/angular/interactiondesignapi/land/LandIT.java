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
import com.team.angular.interactiondesignapi.models.Land_info;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;

import io.restassured.http.ContentType;

public class LandIT extends ItBase {
	
	Land land, land1, land2;
	
	Buchungsklassen buchungsklasse;
	
	Erwartungen erwartungen;
	
	Land_info infos_land;
	
	Land_info leistungen;
	
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
		
		erwartungen = buildErwartungen();
		erwartungen = erwartungenRepository.save(erwartungen);
		
		infos_land = buildInfosLand();
		infos_land = infos_LandRepository.save(infos_land);
		
		reiseAngebot = buildReiseAngebot();
		reiseAngebot = reiseAngebotRepository.save(reiseAngebot);
		
		reiseAngebot1 = buildReiseAngebot();
		reiseAngebot1 = reiseAngebotRepository.save(reiseAngebot1);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		buchungsklasse = buildBuchungsKlasse(land);
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
		LandWriteTO create = buildLandWriteTO(reiseAngebot.getId());
		
		UUID id = UUID.fromString(
				given()
				//.contentType(ContentType.JSON)
				.multiPart("land", create,"application/json")
				.multiPart("bild", "something12354565".getBytes())
				//.body(create)
				.log().body()
				.post("/lands")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land = landRepository.findById(id).get();
		
		assertThat(create.getName(), is(land.getName()));
		//assertThat("something12354565".getBytes(), is(land.getKarte_bild()));
		assertThat(create.getFlughafen(), is(land.getFlughafen()));
		assertThat(create.getUnterkunft_text(), is(land.getUnterkunft_text()));
		assertThat(create.getCorona_infos(), is(land.getCorona_infos()));
		assertThat(create.getHinweise(), is(land.getHinweise()));
		assertThat(create.getMitReiserBerechtigt(), is(land.getMitReiserBerechtigt()));
		assertThat(create.getSonstigeHinweise(), is(land.getSonstigeHinweise()));
		
		assertThat(create.getReiseAngebotId(), is(reiseAngebot.getId()));
	}
	
	@Test
	public void listLands() {	
		
			given()
			.contentType(ContentType.JSON)
			//.body(create)
			.log().body()
			.get("/lands")
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
				.put("/lands")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land_ = landRepository.findById(id).get();
		
		assertThat(update.getId(), is(land_.getId()));
		assertThat(update.getName(), is(land_.getName()));
		//assertThat("something12354565".getBytes(), is(land_.getKarte_bild()));
		assertThat(update.getFlughafen(), is(land_.getFlughafen()));
		assertThat(update.getUnterkunft_text(), is(land_.getUnterkunft_text()));
		assertThat(update.getCorona_infos(), is(land_.getCorona_infos()));
		assertThat(update.getHinweise(), is(land_.getHinweise()));
		assertThat(update.getMitReiserBerechtigt(), is(land_.getMitReiserBerechtigt()));
		assertThat(update.getSonstigeHinweise(), is(land_.getSonstigeHinweise()));
		
		assertThat(update.getReiseAngebotId(), is(reiseAngebot1.getId()));
	}
	
	@Test
	public void getLand() {
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				//.body(land)
				.log().body()
				.get("/lands/"+land.getId() )
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
		assertThat(land.getCorona_infos(), is(land_.getCorona_infos()));
		assertThat(land.getHinweise(), is(land.getHinweise()));
		assertThat(land.getMitReiserBerechtigt().size(), is(land_.getMitReiserBerechtigt().size()));
		assertThat(land.getSonstigeHinweise(), is(land_.getSonstigeHinweise()));
	}
	
	@Test
	public void deleteLand() {

		given()
		.contentType(ContentType.JSON)
		//.body(land)
		.log().body()
		.delete("/lands/"+land.getId())
		.then()
		.log().body()
		.statusCode(200);
			
	}

}
