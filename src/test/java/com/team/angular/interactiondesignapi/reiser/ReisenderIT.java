package com.team.angular.interactiondesignapi.reiser;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.team.angular.interactiondesignapi.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;

import io.restassured.http.ContentType;

public class ReisenderIT extends ItBase {
	
	Reisender reisender, reisender1, mitReisender;
	
	Buchung buchung, buchung1; 
	
	ReisenderWriteTO reiserWrite1;
	
	Land land;
	
	ReiseAngebot reiseAngebot;
	
	private List<String> beschreibung = new ArrayList<>();
	
	Erwartungen erwartungen;
	
	@BeforeEach
	public void setup() {
		super.setup();		
		
		
		reisender = buildReiser();
		reisender = reisenderRepository.save(reisender);
		
		reisender1 = buildReiser();
		reisender1 = reisenderRepository.save(reisender1);
		
		beschreibung.add(UUID.randomUUID().toString());
		
		reiseAngebot = buildReiseAngebot();
		reiseAngebot = reiseAngebotRepository.save(reiseAngebot);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		buchung = buildBuchung(reisender1, reiseAngebot);
		buchung = buchungRepository.save(buchung);
		
		buchung1 = buildBuchung(reisender1, reiseAngebot);
		buchung1 = buchungRepository.save(buchung1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createReiser() {
		
		reiserWrite1 = buildReiserWriteTO();
		
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
		
		Reisender reisender = reisenderRepository.findById(id).get();
		
		assertThat(reiserWrite1.getName(), is(reisender.getName()));
		assertThat(reiserWrite1.getVorname(), is(reisender.getVorname()));
		
	}
	
	@Test
	public void createReiser__Phonenumber_exist() {
		
		reiserWrite1 = buildReiserWriteTO();
		reiserWrite1.setTelefonnummer(reisender1.getTelefonnummer());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
//						.multiPart("reiseAngebot", update,"application/json")
//						.multiPart("bild", "something123".getBytes())
						.body(reiserWrite1)
						.log().body()
						.post("/reisers")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+ reisender1.getTelefonnummer()+" already exists", ex.getLocalizedMessage());
	}
	
	@Test
	public void updateReiser__Phonenumber_exist() {
		
		reiserWrite1 = buildReiserWriteTO();
		reiserWrite1.setId(reisender1.getId());
		reiserWrite1.setTelefonnummer(reisender.getTelefonnummer());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
						.body(reiserWrite1)
						.log().body()
						.put("/reisers")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+ reisender.getTelefonnummer()+" already exists", ex.getLocalizedMessage());
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
		.body("id", containsInAnyOrder(reisender.getId().toString(), reisender1.getId().toString()));
					
	}
	
	@Test
	public void updateReiser() {
		
		reiserWrite1 = buildReiserWriteTO();
		reiserWrite1.setId(reisender.getId());
		
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
		
		Reisender reisender_ = reisenderRepository.findById(id).get();
		
		assertThat(reiserWrite1.getId(), is(reisender_.getId()));
		assertThat(reiserWrite1.getName(), is(reisender_.getName()));
		assertThat(reiserWrite1.getVorname(), is(reisender_.getVorname()));
	}
	
	@Test
	public void getReiser() {
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				//.body(reisender)
				.log().body()
				.get("/reisers/"+ reisender.getId() )
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Reisender reisender_ = reisenderRepository.findById(id).get();
		
		assertThat(reisender.getId(), is(reisender_.getId()));
		assertThat(reisender.getName(), is(reisender_.getName()));
		assertThat(reisender.getVorname(), is(reisender_.getVorname()));
	}
	
	@Test
	public void deleteReiser() {
		
		reisender = buildReiser();
		reisender = reisenderRepository.save(reisender);

		given()
		.contentType(ContentType.JSON)
		//.body(reisender)
		.log().body()
		.delete("/reisers/"+ reisender.getId())
		.then()
		.log().body()
		.statusCode(200);

		assertThat(reisenderRepository.findById(reisender.getId()).isPresent(), is(false));
	}

}
