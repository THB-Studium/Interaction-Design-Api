package com.team.angular.interactiondesignapi.landinfo;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.*;
import com.team.angular.interactiondesignapi.transfertobjects.landInfo.LandInfoReadWriteTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class LandInfoIT extends ItBase {
	
	LandInfo landInfo, landInfo1, landInfo2;
	
	LandInfo buchungsklasse;
	
	ReiseAngebot reiseAngebot;
	
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
		
		land = buildLand(reiseAngebot);
		land = landRepository.save(land);
		
		landInfo = buildInfosLand(land);
		landInfo = landInfoRepository.save(landInfo);
		
		landInfo1 = buildInfosLand(land);
		landInfo1 = landInfoRepository.save(landInfo1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createLandInfo() {
		LandInfoReadWriteTO create = buildLandInfoReadWriteTO(land.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(create)
					.log().body()
					.post("/landInfos")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		LandInfo landInfo = landInfoRepository.findById(id).get();
		
		assertThat(create.getTitel(), is(landInfo.getTitel()));
		assertThat(create.getDescription(), is(landInfo.getDescription()));
		assertThat(create.getLandId(), is(landInfo.getLand().getId()));
	}
	
	@Test
	public void createLandInfo__titel_exist() {
		
		LandInfoReadWriteTO create = buildLandInfoReadWriteTO(land.getId());
		create.setTitel(landInfo.getTitel());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
						.body(create)
						.log().body()
						.post("/landInfos")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: "+landInfo.getTitel()+" already exists", ex.getLocalizedMessage());
	}

	@Test
	public void updateLandInfo__titel_exist() {

		LandInfoReadWriteTO update = buildLandInfoReadWriteTO(land.getId());
		update.setId(landInfo.getId());
		update.setTitel(landInfo1.getTitel());

		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
							.contentType(ContentType.JSON)
							.body(update)
							.log().body()
							.put("/landInfos")
							.then()
							.log().body()
							.statusCode(200)
							.extract().body().path("id"));
		});

		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: " + landInfo1.getTitel() + " already exists", ex.getLocalizedMessage());
	}
	
	@Test
	public void listLandInfos() {	
		
			given()
				.contentType(ContentType.JSON)
				//.body(create)
				.log().body()
				.get("/landInfos")
				.then()
				.log().body()
				.statusCode(200)
				.body("id", containsInAnyOrder(landInfo.getId().toString(), landInfo1.getId().toString()))
				.body("titel", containsInAnyOrder(landInfo.getTitel(), landInfo1.getTitel()))
				.body("description", containsInAnyOrder(landInfo.getDescription(), landInfo1.getDescription()));
	}
	
	@Test
	public void updateLandInfo() {
		
		LandInfoReadWriteTO update = buildLandInfoReadWriteTO(land.getId());
		update.setId(landInfo.getId());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(update)
					.log().body()
					.put("/landInfos")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		LandInfo landInfo_ = landInfoRepository.findById(id).get();
		
		assertThat(update.getId(), is(landInfo_.getId()));
		assertThat(update.getTitel(), is(landInfo_.getTitel()));
		assertThat(update.getDescription(), is(landInfo_.getDescription()));
		assertThat(update.getLandId(), is(landInfo_.getLand().getId()));
	}
	
	@Test
	public void getLandInfo() {
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					//.body(landInfo)
					.log().body()
					.get("/landInfos/"+landInfo.getId() )
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		LandInfo landInfo_ = landInfoRepository.findById(id).get();
		
		assertThat(landInfo.getId(), is(landInfo_.getId()));
		assertThat(landInfo.getTitel(), is(landInfo_.getTitel()));
		assertThat(landInfo.getDescription(), is(landInfo_.getDescription()));
		assertThat(landInfo.getLand().getId(), is(landInfo_.getLand().getId()));
	}
	
	@Test
	public void deleteLandInfo() {

		given()
			.contentType(ContentType.JSON)
			//.body(landInfo)
			.log().body()
			.delete("/landInfos/"+landInfo.getId())
			.then()
			.log().body()
			.statusCode(200);	
		
		assertThat(landInfoRepository.findById(landInfo.getId()).isPresent(), is(false));
	}

}
