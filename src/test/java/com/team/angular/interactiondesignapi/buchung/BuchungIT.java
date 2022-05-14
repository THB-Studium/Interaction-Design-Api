package com.team.angular.interactiondesignapi.buchung;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.*;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class BuchungIT extends ItBase {

    Buchung buchung, buchung1, buchung2;

    Buchungsklassen buchungsklasse, buchungsklasse1;

    ReisenderWriteTO reiserWrite1;

    Land land, land1;
    Reisender reisender, reisender1, mitReisender, reisender2, mitReisender1;
    Erwartungen erwartungen;
    ReiseAngebot reiseAngebot, reiseAngebot1;
    private List<String> beschreibung = new ArrayList<>();

    @BeforeEach
    public void setup() {
        super.setup();

        reisender = buildReisender();
        reisender = reisenderRepository.save(reisender);

        reisender1 = buildReisender();
        reisender1 = reisenderRepository.save(reisender1);

        reisender2 = buildReisender();
        reisender2 = reisenderRepository.save(reisender2);

        mitReisender1 = buildReisender();
        mitReisender1 = reisenderRepository.save(mitReisender1);

        mitReisender = buildReisender();
        mitReisender = reisenderRepository.save(mitReisender);

        beschreibung.add(UUID.randomUUID().toString());

        reiseAngebot = buildReiseAngebot();
        reiseAngebot = reiseAngebotRepository.save(reiseAngebot);

        reiseAngebot1 = buildReiseAngebot();
        reiseAngebot1 = reiseAngebotRepository.save(reiseAngebot1);

        land = buildLand(reiseAngebot);
        land = landRepository.save(land);

        buchungsklasse = buildBuchungsKlasse(reiseAngebot);
        buchungsklasse = buchungsklasseRepository.save(buchungsklasse);

        buchungsklasse1 = buildBuchungsKlasse(reiseAngebot);
        buchungsklasse1 = buchungsklasseRepository.save(buchungsklasse);

        buchung = buildBuchung(reisender, reiseAngebot);
        buchung = buchungRepository.save(buchung);

        buchung1 = buildBuchung(reisender1, reiseAngebot);
        buchung1 = buchungRepository.save(buchung1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createBuchung() {
        BuchungWriteTO create = buildBuchungWriteTO(buchungsklasse.getId(), reiseAngebot.getId());

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(create)
                        .log().body()
                        .post("/buchungen")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Buchung buchung = buchungRepository.findById(id).get();

        assertThat(create.getBuchungDatum(), is(buchung.getBuchungDatum()));
        assertThat(create.getReiseAngebotId(), is(buchung.getReiseAngebot().getId()));
        assertThat(create.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
        assertThat(create.getFlughafen(), is(buchung.getFlughafen()));
        assertThat(Buchungstatus.Eingegangen, is(buchung.getStatus()));
        //assertThat(reiseAngebot.getFreiPlaetze(), is(reiseAngebot.getFreiPlaetze() - 1));
    }

    @Test
    public void listBuchungs() {

        given()
                .contentType(ContentType.JSON)
                //.body(create)
                .log().body()
                .get("/buchungen")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", containsInAnyOrder(buchung.getId().toString(), buchung1.getId().toString()));

    }

    @Test
    public void updateBuchung() {

        BuchungUpdateTO update = buildBuchungUpdateTO(buchungsklasse1.getId(), reiseAngebot1.getId(), mitReisender1.getId(), reisender1.getId());
        update.setId(buchung.getId());

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(update)
                        .log().body()
                        .put("/buchungen")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Buchung buchung = buchungRepository.findById(id).get();

        assertThat(update.getBuchungDatum(), is(buchung.getBuchungDatum()));
        assertThat(update.getReiseAngebotId(), is(buchung.getReiseAngebot().getId()));
        assertThat(update.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
        assertThat(update.getFlughafen(), is(buchung.getFlughafen()));
        assertThat(update.getReisenderId(), is(reisender1.getId()));
        assertThat(update.getMitReisenderId(), is(mitReisender1.getId()));
        assertThat(update.getFlughafen(), is(buchung.getFlughafen()));
    }

    @Test
    public void updateBuchung_status() {

        BuchungUpdateTO update = buildBuchungUpdateTO(buchungsklasse1.getId(), reiseAngebot1.getId(), mitReisender1.getId(), reisender1.getId());
        update.setId(buchung.getId());
        update.setStatus(Buchungstatus.Bearbeitung);

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(update)
                        .log().body()
                        .put("/buchungen")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Buchung buchung = buchungRepository.findById(id).get();

        assertThat(update.getBuchungDatum(), is(buchung.getBuchungDatum()));
        assertThat(update.getReiseAngebotId(), is(buchung.getReiseAngebot().getId()));
        assertThat(update.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
        assertThat(update.getFlughafen(), is(buchung.getFlughafen()));
        assertThat(update.getReisenderId(), is(reisender1.getId()));
        assertThat(update.getMitReisenderId(), is(mitReisender1.getId()));
        assertThat(update.getFlughafen(), is(buchung.getFlughafen()));
        //assertThat(update.getStatus(), is(buchung.getStatus())); //todo: will use new route
    }

    @Test
    public void getBuchung() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        //.body(buchung)
                        .log().body()
                        .get("/buchungen/" + buchung.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Buchung buchung_ = buchungRepository.findById(id).get();

        assertThat(buchung.getId(), is(buchung_.getId()));
        assertThat(buchung.getBuchungDatum(), is(buchung_.getBuchungDatum()));
    }

    @Test
    public void deleteBuchung() {

        given()
                .contentType(ContentType.JSON)
                //.body(buchung)
                .log().body()
                .delete("/buchungen/" + buchung.getId())
                .then()
                .log().body()
                .statusCode(200);

        assertThat(buchungRepository.findById(buchung.getId()).isPresent(), is(false));
    }

}
