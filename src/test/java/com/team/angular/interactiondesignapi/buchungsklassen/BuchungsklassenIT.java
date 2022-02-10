package com.team.angular.interactiondesignapi.buchungsklassen;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadWriteTO;

import io.restassured.http.ContentType;

public class BuchungsklassenIT extends ItBase {
	
	Buchungsklassen buchungsklassen, buchungsklassen1, buchungsklassen2;
	
	Buchungsklassen buchungsklasse;
	
	ReiseAngebot reiseAngebot;
	
	Erwartungen erwartungen;
	
	Land land;
	
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
		
		buchungsklassen = buildBuchungsKlasse(reiseAngebot);
		buchungsklassen = buchungsklasseRepository.save(buchungsklassen);
		
		buchungsklassen1 = buildBuchungsKlasse(reiseAngebot);
		buchungsklassen1 = buchungsklasseRepository.save(buchungsklassen1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createBuchungsklassen() {
		BuchungsklassenReadWriteTO create = buildBuchungsKlasseReadWriteTO(reiseAngebot.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(create)
					.log().body()
					.post("/buchungsklassen")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Buchungsklassen buchungsklassen = buchungsklasseRepository.findById(id).get();
		
		assertThat(create.getType(), is(buchungsklassen.getType()));
		assertThat(create.getPreis(), is(buchungsklassen.getPreis()));
		assertThat(create.getDescription(), is(buchungsklassen.getDescription()));
		assertThat(create.getReiseAngebotId(), is(buchungsklassen.getReiseAngebot().getId()));
	}
	
	@Test
	public void listBuchungsklassens() {	
		
			given()
				.contentType(ContentType.JSON)
				//.body(create)
				.log().body()
				.get("/buchungsklassen")
				.then()
				.log().body()
				.statusCode(200)
				.body("id", containsInAnyOrder(buchungsklassen.getId().toString(), buchungsklassen1.getId().toString()))
				.body("type", containsInAnyOrder(buchungsklassen.getType(), buchungsklassen1.getType()));
	}
	
	@Test
	public void updateBuchungsklassen() {
		
		BuchungsklassenReadWriteTO update = buildBuchungsKlasseReadWriteTO(reiseAngebot.getId());
		update.setId(buchungsklassen.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(update)
					.log().body()
					.put("/buchungsklassen")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Buchungsklassen buchungsklassen_ = buchungsklasseRepository.findById(id).get();
		
		assertThat(update.getId(), is(buchungsklassen_.getId()));
		assertThat(update.getType(), is(buchungsklassen_.getType()));
		assertThat(update.getPreis(), is(buchungsklassen_.getPreis()));
		assertThat(update.getDescription(), is(buchungsklassen_.getDescription()));
		assertThat(update.getReiseAngebotId(), is(buchungsklassen_.getReiseAngebot().getId()));
	}
	
	@Test
	public void getBuchungsklassen() {
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					//.body(buchungsklassen)
					.log().body()
					.get("/buchungsklassen/"+buchungsklassen.getId() )
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Buchungsklassen buchungsklassen_ = buchungsklasseRepository.findById(id).get();
		
		assertThat(buchungsklassen.getId(), is(buchungsklassen_.getId()));
		assertThat(buchungsklassen.getType(), is(buchungsklassen_.getType()));
		assertThat(buchungsklassen.getPreis(), is(buchungsklassen_.getPreis()));
		assertThat(buchungsklassen.getDescription(), is(buchungsklassen_.getDescription()));
		assertThat(buchungsklassen.getReiseAngebot().getId(), is(buchungsklassen_.getReiseAngebot().getId()));
	}
	
	@Test
	public void deleteBuchungsklassen() {

		given()
			.contentType(ContentType.JSON)
			//.body(buchungsklassen)
			.log().body()
			.delete("/buchungsklassen/"+buchungsklassen.getId())
			.then()
			.log().body()
			.statusCode(200);	
		
		assertThat(buchungsklasseRepository.findById(buchungsklassen.getId()).isPresent(), is(false));
	}

}
