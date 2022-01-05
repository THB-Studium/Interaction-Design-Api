package com.team.angular.interactiondesignapi.erwartungen;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadWriteTO;

import io.restassured.http.ContentType;

public class ErwartungenIT extends ItBase {
	
	Erwartungen erwartungen, erwartungen1, erwartungen2;
	
	Erwartungen buchungsklasse;
	
	ReiseAngebot reiseAngebot;
	
	Land land;

	Reiser reiser, reiser1, mitReiser;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		reiseAngebot = buildReiseAngebot();
		reiseAngebot = reiseAngebotRepository.save(reiseAngebot);
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		erwartungen = buildErwartungen(reiseAngebot);
		erwartungen = erwartungenRepository.save(erwartungen);
		
		erwartungen1 = buildErwartungen(reiseAngebot);
		erwartungen1 = erwartungenRepository.save(erwartungen1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createErwartungen() {
		ErwartungenReadWriteTO create = buildErwartungenReadWriteTO(reiseAngebot.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(create)
					.log().body()
					.post("/erwartungen")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Erwartungen erwartungen = erwartungenRepository.findById(id).get();
		
		assertThat(create.getAbenteuer(), is(erwartungen.getAbenteuer()));
		assertThat(create.getEntschleunigung(), is(erwartungen.getEntschleunigung()));
		assertThat(create.getKonfort(), is(erwartungen.getKonfort()));
		assertThat(create.getNachhaltigkeit(), is(erwartungen.getNachhaltigkeit()));
		assertThat(create.getSonne_strand(), is(erwartungen.getSonne_strand()));
		assertThat(create.getSicherheit(), is(erwartungen.getSicherheit()));
		assertThat(create.getRoad(), is(erwartungen.getRoad()));
		
		// TODO check the null pointer
		//assertThat(create.getReiseAngebotId(), is(erwartungen.getReiseAngebot().getId()));
	}
	
	@Test
	public void listErwartungens() {	
		
			given()
				.contentType(ContentType.JSON)
				//.body(create)
				.log().body()
				.get("/erwartungen")
				.then()
				.log().body()
				.statusCode(200)
				.body("id", containsInAnyOrder(erwartungen.getId().toString(), erwartungen1.getId().toString()))
				.body("abenteuer", containsInAnyOrder(erwartungen.getAbenteuer(), erwartungen1.getAbenteuer()))
				.body("entschleunigung", containsInAnyOrder(erwartungen.getEntschleunigung(), erwartungen1.getEntschleunigung()))
				.body("konfort", containsInAnyOrder(erwartungen.getKonfort(), erwartungen1.getKonfort()))
				.body("nachhaltigkeit", containsInAnyOrder(erwartungen.getNachhaltigkeit(), erwartungen1.getNachhaltigkeit()))
				.body("sonne_strand", containsInAnyOrder(erwartungen.getSonne_strand(), erwartungen1.getSonne_strand()))
				.body("sicherheit", containsInAnyOrder(erwartungen.getSicherheit(), erwartungen1.getSicherheit()))
				.body("road", containsInAnyOrder(erwartungen.getRoad(), erwartungen1.getRoad()));
	}
	
	@Test
	public void updateErwartungen() {
		
		ErwartungenReadWriteTO update = buildErwartungenReadWriteTO(reiseAngebot.getId());
		update.setId(erwartungen.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(update)
					.log().body()
					.put("/erwartungen")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Erwartungen erwartungen_ = erwartungenRepository.findById(id).get();
		
		assertThat(update.getId(), is(erwartungen_.getId()));
		assertThat(update.getAbenteuer(), is(erwartungen_.getAbenteuer()));
		assertThat(update.getEntschleunigung(), is(erwartungen_.getEntschleunigung()));
		assertThat(update.getKonfort(), is(erwartungen_.getKonfort()));
		assertThat(update.getNachhaltigkeit(), is(erwartungen_.getNachhaltigkeit()));
		assertThat(update.getSonne_strand(), is(erwartungen_.getSonne_strand()));
		assertThat(update.getSicherheit(), is(erwartungen_.getSicherheit()));
		assertThat(update.getRoad(), is(erwartungen_.getRoad()));
	}
	
	@Test
	public void getErwartungen() {
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					//.body(erwartungen)
					.log().body()
					.get("/erwartungen/"+erwartungen.getId() )
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Erwartungen erwartungen_ = erwartungenRepository.findById(id).get();
		
		assertThat(erwartungen.getId(), is(erwartungen_.getId()));
		assertThat(erwartungen.getAbenteuer(), is(erwartungen_.getAbenteuer()));
		assertThat(erwartungen.getEntschleunigung(), is(erwartungen_.getEntschleunigung()));
		assertThat(erwartungen.getKonfort(), is(erwartungen_.getKonfort()));
		assertThat(erwartungen.getNachhaltigkeit(), is(erwartungen_.getNachhaltigkeit()));
		assertThat(erwartungen.getSonne_strand(), is(erwartungen_.getSonne_strand()));
		assertThat(erwartungen.getSicherheit(), is(erwartungen_.getSicherheit()));
		assertThat(erwartungen.getRoad(), is(erwartungen_.getRoad()));
		
		// TODO check the null pointer
		//assertThat(erwartungen.getReiseAngebot(), is(erwartungen_.getReiseAngebot().getId()));
	}
	
	@Test
	public void deleteErwartungen() {

		given()
			.contentType(ContentType.JSON)
			//.body(erwartungen)
			.log().body()
			.delete("/erwartungen/"+erwartungen.getId())
			.then()
			.log().body()
			.statusCode(200);	
		
		assertThat(erwartungenRepository.findById(erwartungen.getId()).isPresent(), is(false));
	}

}
