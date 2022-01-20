package com.team.angular.interactiondesignapi.reiseangebot;

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
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot.ReiseAngebotWriteTO;

import io.restassured.http.ContentType;

public class ReiseAngebotIT extends ItBase {
	
	ReiseAngebot reiseAngebot, reiseAngebot1, reiseAngebot2;
	
	Buchungsklassen buchungsklasse;
	
	Erwartungen erwartungen;
	
	Land land;
	
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
		
		buchungsklasse = buildBuchungsKlasse(reiseAngebot1);
		buchungsklasseRepository.save(buchungsklasse);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createReiseAngebot() {
		ReiseAngebotWriteTO create = buildReiseAngebotWriteTO(land.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
//					.multiPart("reiseAngebot", create,"application/json")
//					.multiPart("bild", "something12354565".getBytes())
					.body(create)
					.log().body()
					.post("/reiseAngebot")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		ReiseAngebot reiseAngebot = reiseAngebotRepository.findById(id).get();
		
		assertThat(create.getTitel(), is(reiseAngebot.getTitel()));
		//assertThat(create.getStartbild().getBytes(), is(reiseAngebot.getStartbild()));
		assertThat(create.getStartDatum(), is(reiseAngebot.getStartDatum()));
		assertThat(create.getEndDatum(), is(reiseAngebot.getEndDatum()));
		assertThat(create.getFreiPlaetze(), is(reiseAngebot.getFreiPlaetze()));
		assertThat(create.getInteressiert(), is(reiseAngebot.getInteressiert()));
		assertThat(create.getAnmeldungsFrist(), is(reiseAngebot.getAnmeldungsFrist()));
		assertThat(create.getHinweise(), is(reiseAngebot.getHinweise()));
		assertThat(create.getLandId(), is(reiseAngebot.getLand().getId()));
		assertThat(create.getMitReiserBerechtigt(), is(reiseAngebot.getMitReiserBerechtigt()));
		assertThat(create.getSonstigeHinweise(), is(reiseAngebot.getSonstigeHinweise()));
	}
	
	@Test
	public void createReiseAngebot__titel_exist() {
		
		ReiseAngebotWriteTO create = buildReiseAngebotWriteTO(land.getId());
		create.setId(reiseAngebot.getId());
		create.setTitel(reiseAngebot1.getTitel());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID id = UUID.fromString(
					given()
						.contentType(ContentType.JSON)
//						.multiPart("reiseAngebot", update,"application/json")
//						.multiPart("bild", "something123".getBytes())
						.body(create)
						.log().body()
						.post("/reiseAngebot")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+reiseAngebot1.getTitel()+" already exists", ex.getLocalizedMessage());
	}
	
	@Test
	public void updateReiseAngebot__titel_exist() {
		
		ReiseAngebotWriteTO update = buildReiseAngebotWriteTO(land.getId());
		update.setId(reiseAngebot.getId());
		update.setTitel(reiseAngebot1.getTitel());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID id = UUID.fromString(
					given()
						.contentType(ContentType.JSON)
//						.multiPart("reiseAngebot", update,"application/json")
//						.multiPart("bild", "something123".getBytes())
						.body(update)
						.log().body()
						.put("/reiseAngebot")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+reiseAngebot1.getTitel()+" already exists", ex.getLocalizedMessage());
	}
	
	@Test
	public void listReiseAngebots() {	
		
			given()
				.contentType(ContentType.JSON)
				//.body(create)
				.log().body()
				.get("/reiseAngebot")
				.then()
				.log().body()
				.statusCode(200)
				.body("id", containsInAnyOrder(reiseAngebot.getId().toString(), reiseAngebot1.getId().toString()))
				.body("titel", containsInAnyOrder(reiseAngebot.getTitel(), reiseAngebot1.getTitel()));
				
	}
	
	@Test
	public void updateReiseAngebot() {
		
		ReiseAngebotWriteTO update = buildReiseAngebotWriteTO(land.getId());
		update.setId(reiseAngebot.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
//					.multiPart("reiseAngebot", update,"application/json")
//					.multiPart("bild", "something123".getBytes())
					.body(update)
					.log().body()
					.put("/reiseAngebot")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		ReiseAngebot reiseAngebot_ = reiseAngebotRepository.findById(id).get();
		
		assertThat(update.getTitel(), is(reiseAngebot_.getTitel()));
		//assertThat(update.getStartbild().getBytes(), is(reiseAngebot_.getStartbild()));
		assertThat(update.getStartDatum(), is(reiseAngebot_.getStartDatum()));
		assertThat(update.getEndDatum(), is(reiseAngebot_.getEndDatum()));
		assertThat(update.getFreiPlaetze(), is(reiseAngebot_.getFreiPlaetze()));
		assertThat(update.getInteressiert(), is(reiseAngebot_.getInteressiert()));
		assertThat(update.getAnmeldungsFrist(), is(reiseAngebot_.getAnmeldungsFrist()));
		assertThat(update.getHinweise(), is(reiseAngebot_.getHinweise()));
		assertThat(update.getMitReiserBerechtigt(), is(reiseAngebot_.getMitReiserBerechtigt()));
		assertThat(update.getSonstigeHinweise(), is(reiseAngebot_.getSonstigeHinweise()));
	}
	
	@Test
	public void getReiseAngebot() {
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					//.body(reiseAngebot)
					.log().body()
					.get("/reiseAngebot/"+reiseAngebot.getId() )
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		ReiseAngebot reiseAngebot_ = reiseAngebotRepository.findById(id).get();
		
		assertThat(reiseAngebot.getId(), is(reiseAngebot_.getId()));
		//assertThat("something12354565".getBytes(), is(reiseAngebot_.getStartbild()));
		assertThat(reiseAngebot.getStartDatum(), is(reiseAngebot_.getStartDatum()));
		assertThat(reiseAngebot.getEndDatum(), is(reiseAngebot_.getEndDatum()));
		assertThat(reiseAngebot.getFreiPlaetze(), is(reiseAngebot_.getFreiPlaetze()));
		assertThat(reiseAngebot.getInteressiert(), is(reiseAngebot_.getInteressiert()));
		assertThat(reiseAngebot.getAnmeldungsFrist(), is(reiseAngebot_.getAnmeldungsFrist()));
		assertThat(reiseAngebot.getHinweise(), is(reiseAngebot_.getHinweise()));
		assertThat(reiseAngebot.getLand(), is(reiseAngebot_.getLand()));
		// TODO check also the array content
		assertThat(reiseAngebot.getMitReiserBerechtigt().size(), is(reiseAngebot_.getMitReiserBerechtigt().size()));
		assertThat(reiseAngebot.getSonstigeHinweise(), is(reiseAngebot_.getSonstigeHinweise()));
	}
	
	@Test
	public void deleteReiseAngebot() {

		given()
			.contentType(ContentType.JSON)
			//.body(reiseAngebot)
			.log().body()
			.delete("/reiseAngebot/"+reiseAngebot.getId())
			.then()
			.log().body()
			.statusCode(200);
		
		assertThat(reiseAngebotRepository.findById(reiseAngebot.getId()).isPresent(), is(false));
	}

}
