package com.team.angular.interactiondesignapi.reisender;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.*;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;
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

public class ReisenderIT extends ItBase {

    Reisender reisender, reisender1, mitReisender;

    Buchung buchung, buchung1;

    ReisenderWriteTO reisenderWrite1;

    Land land;

    ReiseAngebot reiseAngebot;
    Erwartungen erwartungen;
    private List<String> beschreibung = new ArrayList<>();

    @BeforeEach
    public void setup() {
        super.setup();


        reisender = buildReisender();
        reisender = reisenderRepository.save(reisender);

        reisender1 = buildReisender();
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
    public void createReisender() {

        reisenderWrite1 = buildReisenderWriteTO();

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(reisenderWrite1)
                        .log().body()
                        .post("/reisende")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Reisender reisender = reisenderRepository.findById(id).get();

        assertThat(reisenderWrite1.getName(), is(reisender.getName()));
        assertThat(reisenderWrite1.getVorname(), is(reisender.getVorname()));

    }

    @Test
    public void createReisender__Phonenumber_exist() {

        reisenderWrite1 = buildReisenderWriteTO();
        reisenderWrite1.setTelefonnummer(reisender1.getTelefonnummer());

        String ex = given()
                .contentType(ContentType.JSON)
//						.multiPart("reiseAngebot", update,"application/json")
//						.multiPart("bild", "something123".getBytes())
                .body(reisenderWrite1)
                .log().body()
                .post("/reisende")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals(reisender1.getTelefonnummer() + " already exists", ex);
    }

    @Test
    public void updateReisender__Phonenumber_exist() {

        reisenderWrite1 = buildReisenderWriteTO();
        reisenderWrite1.setId(reisender1.getId());
        reisenderWrite1.setTelefonnummer(reisender.getTelefonnummer());

        String ex = given()
                .contentType(ContentType.JSON)
                .body(reisenderWrite1)
                .log().body()
                .put("/reisende")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals(reisender.getTelefonnummer() + " already exists", ex);
    }

    @Test
    public void listReisenders() {

        given()
                .contentType(ContentType.JSON)
                .body("")
                .log().body()
                .get("/reisende")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", containsInAnyOrder(reisender.getId().toString(), reisender1.getId().toString()));

    }

    @Test
    public void updateReisender() {

        reisenderWrite1 = buildReisenderWriteTO();
        reisenderWrite1.setId(reisender.getId());
        reisenderWrite1.setTelefonnummer(null);
        reisenderWrite1.setSchonTeilgenommen(true);

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(reisenderWrite1)
                        .log().body()
                        .put("/reisende")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Reisender reisender_ = reisenderRepository.findById(id).get();

        assertThat(reisenderWrite1.getId(), is(reisender_.getId()));
        assertThat(reisenderWrite1.getName(), is(reisender_.getName()));
        assertThat(reisenderWrite1.getVorname(), is(reisender_.getVorname()));
    }

    @Test
    public void getReisender() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        //.body(reisender)
                        .log().body()
                        .get("/reisende/" + reisender.getId())
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
    public void deleteReisender() {

        reisender = buildReisender();
        reisender = reisenderRepository.save(reisender);

        given()
                .contentType(ContentType.JSON)
                //.body(reisender)
                .log().body()
                .delete("/reisende/" + reisender.getId())
                .then()
                .log().body()
                .statusCode(200);

        assertThat(reisenderRepository.findById(reisender.getId()).isPresent(), is(false));
    }

}
