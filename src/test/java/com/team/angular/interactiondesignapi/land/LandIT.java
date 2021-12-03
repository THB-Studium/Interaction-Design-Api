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
import com.team.angular.interactiondesignapi.models.Infos_land;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Leistungen;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.transfertobjects.land.LandWriteTO;

import io.restassured.http.ContentType;

public class LandIT extends ItBase {
	
	Land land, land1, land2;
	
	Buchungsklassen buchungsklasse;
	
	Erwartungen erwartungen;
	
	Infos_land infos_land;
	
	Leistungen leistungen;
	
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
		
		leistungen = buildLeistungen(beschreibung);
		leistungen = leistungenRepository.save(leistungen);
		
		erwartungen = buildErwartungen();
		erwartungen = erwartungenRepository.save(erwartungen);
		
		infos_land = buildInfosLand(abflug, mitreiseberechtigt);
		infos_land = infos_LandRepository.save(infos_land);
		
		land = buildLand(erwartungen);
		land = landRepository.save(land);
		
		buchungsklasse = buildBuchungsKlasse(land);
		buchungsklasseRepository.save(buchungsklasse);
		
		land1 = buildLand(erwartungen);
		land1 = landRepository.save(land1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createLand() {
		LandWriteTO create = buildLandWriteTO(erwartungen.getId(), infos_land.getId(), buchungsklasse.getId());
		
		UUID id = UUID.fromString(
				given()
				//.contentType(ContentType.JSON)
				.multiPart("land", create,"application/json")
				.multiPart("bild", "something123".getBytes())
				.multiPart("karteBild", "something12354565".getBytes())
				//.body(create)
				.log().body()
				.post("/lands")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land = landRepository.findById(id).get();
		
		assertThat(create.getName(), is(land.getName()));
//		assertThat("something123".getBytes(), is(land.getBild()));
//		assertThat("something12354565".getBytes(), is(land.getKarteBild()));
		assertThat(create.getTitel(), is(land.getTitel()));
		assertThat(create.getPlaetze(), is(land.getPlaetze()));
		assertThat(create.getFreiPlaetze(), is(land.getFreiPlaetze()));
		assertThat(create.getErwartungenId(), is(land.getErwartungen().getId()));
		assertThat(create.getInfos_LandId(), is(land.getInfos_Land().getId()));
		assertThat(create.getBuchungsklassenId(), is(land.getBuchungsklassen().getId()));
		
		
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
		
		LandWriteTO update = buildLandWriteTO(erwartungen.getId(), infos_land.getId(), buchungsklasse.getId());
		update.setId(land.getId());
		
		UUID id = UUID.fromString(
				given()
				//.contentType(ContentType.JSON)
				.multiPart("land", update,"application/json")
				.multiPart("bild", "something123".getBytes())
				.multiPart("karteBild", "something12354565".getBytes())
				//.body(create)
				.log().body()
				.put("/lands")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Land land_ = landRepository.findById(id).get();
		
		assertThat(land.getId(), is(land_.getId()));
		assertThat(update.getName(), is(land_.getName()));
		assertThat(update.getTitel(), is(land_.getTitel()));		
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
		assertThat(land.getName(), is(land_.getName()));
		assertThat(land.getTitel(), is(land_.getTitel()));		
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
