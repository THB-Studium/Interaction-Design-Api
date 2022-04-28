package com.team.angular.interactiondesignapi.unterkunft;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Unterkunft;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftWriteTO;
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

public class UnterkunftIT extends ItBase {

    Unterkunft unterkunft, unterkunft1, unterkunft2;

    UnterkunftWriteTO unterkunftWrite1;

    Land land;

    ReiseAngebot reiseAngebot;
    Erwartungen erwartungen;
    private List<byte[]> bilder = new ArrayList<>();
    private List<String> beschreibung = new ArrayList<>();

    @BeforeEach
    public void setup() {
        super.setup();


        bilder.add(UUID.randomUUID().toString().getBytes());
        bilder.add(UUID.randomUUID().toString().getBytes());
        bilder.add(UUID.randomUUID().toString().getBytes());

        beschreibung.add(UUID.randomUUID().toString());

        reiseAngebot = buildReiseAngebot();
        reiseAngebot = reiseAngebotRepository.save(reiseAngebot);

        land = buildLand(reiseAngebot);
        land = landRepository.save(land);

        unterkunft = buildUnterkunft(bilder, land);
        unterkunft = unterkunftRepository.save(unterkunft);

        unterkunft1 = buildUnterkunft(bilder, land);
        unterkunft1 = unterkunftRepository.save(unterkunft1);

        unterkunft2 = buildUnterkunft(bilder, land);
        unterkunft2 = unterkunftRepository.save(unterkunft2);
    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createUnterkunft() {

        unterkunftWrite1 = buildUnterkunftWriteTO(land.getId());


        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
//				.multiPart("unterkunft", unterkunftWrite1,"application/json")
//				.multiPart("files", "something123".getBytes())
                        .body(unterkunftWrite1)
                        .log().body()
                        .post("/unterkunfte")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Unterkunft unterkunft = unterkunftRepository.findById(id).get();

        assertThat(unterkunftWrite1.getName(), is(unterkunft.getName()));
//		assertThat(unterkunft.getLand().getUnterkunft(), containsInAnyOrder(unterkunft));

    }

    @Test
    public void createUnterkunft__name_exist() {

        unterkunftWrite1 = buildUnterkunftWriteTO(land.getId());
        unterkunftWrite1.setName(unterkunft.getName());

        String ex = given()
                .contentType(ContentType.JSON)
                .body(unterkunftWrite1)
                .log().body()
                .post("/unterkunfte")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals(unterkunft.getName() + " already exists", ex);
    }

    @Test
    public void updateUnterkunft__name_exist() {

        unterkunftWrite1 = buildUnterkunftWriteTO(land.getId());
        unterkunftWrite1.setId(unterkunft.getId());
        unterkunftWrite1.setName(unterkunft1.getName());

        String ex = given()
                .contentType(ContentType.JSON)
                .body(unterkunftWrite1)
                .log().body()
                .put("/unterkunfte")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals(unterkunft1.getName() + " already exists", ex);
    }

    @Test
    public void listUnterkunfts() {

        given()
                .contentType(ContentType.JSON)
                //.body(create)
                .log().body()
                .get("/unterkunfte")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", containsInAnyOrder(unterkunft.getId().toString(),
                        unterkunft1.getId().toString(), unterkunft2.getId().toString()));

    }

    @Test
    public void updateUnterkunft() {
        unterkunftWrite1 = buildUnterkunftWriteTO(land.getId());
        unterkunftWrite1.setId(unterkunft.getId());

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
//				.multiPart("unterkunft", unterkunftWrite1,"application/json")
//				.multiPart("files", "something123".getBytes())
                        .body(unterkunftWrite1)
                        .log().body()
                        .put("/unterkunfte")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Unterkunft updated_unterkunft = unterkunftRepository.findById(id).get();

        assertThat(unterkunft.getId(), is(updated_unterkunft.getId()));
        assertThat(unterkunftWrite1.getName(), is(updated_unterkunft.getName()));
        assertThat(unterkunftWrite1.getLink(), is(updated_unterkunft.getLink()));
        assertThat(unterkunftWrite1.getAdresse(), is(updated_unterkunft.getAdresse()));
        assertThat(unterkunftWrite1.getBeschreibung(), is(updated_unterkunft.getBeschreibung()));
        assertThat(unterkunftWrite1.getLandId(), is(updated_unterkunft.getLand().getId()));
    }

    @Test
    public void getUnterkunft() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        //.body(Unterkunft)
                        .log().body()
                        .get("/unterkunfte/" + unterkunft.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Unterkunft unterkunft_ = unterkunftRepository.findById(id).get();

        assertThat(unterkunft.getId(), is(unterkunft_.getId()));
    }

    @Test
    public void deleteUnterkunft() {

        unterkunft = buildUnterkunft(bilder, land);
        unterkunft = unterkunftRepository.save(unterkunft);

        given()
                .contentType(ContentType.JSON)
                //.body(Unterkunft)
                .log().body()
                .delete("/unterkunfte/" + unterkunft.getId())
                .then()
                .log().body()
                .statusCode(200);

        assertThat(unterkunftRepository.findById(unterkunft.getId()).isPresent(), is(false));
    }

}
