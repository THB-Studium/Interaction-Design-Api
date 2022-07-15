package com.team.angular.interactiondesignapi.buchung;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Buchungstatus;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.ChangeStatus;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;

import io.restassured.http.ContentType;

public class BuchungIT extends ItBase {

    Buchung buchung, buchung1, buchung2;

    Buchungsklassen buchungsklasse, buchungsklasse1;

    ReisenderWriteTO reiserWrite1;

    Land land, land1;
    Reisender reisender, reisender1, mitReisender, reisender2, mitReisender1;
    Erwartungen erwartungen;
    ReiseAngebot reiseAngebot, reiseAngebot1;
    private List<String> beschreibung = new ArrayList<>();

    @Autowired
    private BuchungRepository buchungRepository;

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
        buchung.setBuchungsklasseId(buchungsklasse.getId());
        buchung.setMitReisenderId(null);
        buchung.setNummer(1);
        buchung = buchungRepository.save(buchung);

        buchung1 = buildBuchung(reisender1, reiseAngebot);
        buchung1.setNummer(2);
        buchung1 = buchungRepository.save(buchung1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


//    @Test
//    public void createBuchung() {
//        BuchungWriteTO create = buildBuchungWriteTO(buchungsklasse.getId(), reiseAngebot.getId());        
//
//        UUID id = UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        .body(create)
//                        .log().body()
//                        .post("/buchungen")
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//        Buchung buchung = buchungRepository.findById(id).get();
//
//        assertThat(create.getBuchungDatum(), is(buchung.getBuchungDatum()));
//        assertThat(create.getReiseAngebotId(), is(buchung.getReiseAngebot().getId()));
//        assertThat(create.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
//
//        assertThat(create.getAbFlughafenReisender(), is(buchung.getAbFlughafenReisender()));
//        assertThat(create.getAbFlughafenMitReisender(), is(buchung.getAbFlughafenMitReisender()));
//        assertThat(create.getRuckFlughafenReisender(), is(buchung.getRuckFlughafenReisender()));
//        assertThat(create.getRuckFlughafenMitReisender(), is(buchung.getRuckFlughafenMitReisender()));
//        assertThat(create.getHandGepaeckReisender(), is(buchung.getHandGepaeckReisender()));
//        assertThat(create.getHandGepaeckMitReisender(), is(buchung.getHandGepaeckMitReisender()));
//        assertThat(create.getKofferReisender(), is(buchung.getKofferReisender()));
//        assertThat(create.getKofferMitReisender(), is(buchung.getKofferMitReisender()));
//
//        assertThat(Buchungstatus.Eingegangen, is(buchung.getStatus()));
//    }

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

//    @Test
//    public void listBuchungs_() {
//
//        BuchungWriteTO create = buildBuchungWriteTO(buchungsklasse.getId(), reiseAngebot.getId());
//
//        UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        .body(create)
//                        .log().body()
//                        .post("/buchungen")
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//
//        Optional<Buchung> buchung = buchungRepository.findFirstByOrderByNummerDesc();
//
//    }

    @Test
    public void updateBuchung() {

        BuchungUpdateTO update = buildBuchungUpdateTO(buchungsklasse1.getId(), reiseAngebot1.getId(), mitReisender1.getId(), reisender1.getId());
        update.setId(buchung.getId());
        update.setStatus(Buchungstatus.Bestaetigt);

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

        assertThat(update.getAbFlughafenReisender(), is(buchung.getAbFlughafenReisender()));
        assertThat(update.getAbFlughafenMitReisender(), is(buchung.getAbFlughafenMitReisender()));
        assertThat(update.getRuckFlughafenReisender(), is(buchung.getRuckFlughafenReisender()));
        assertThat(update.getRuckFlughafenMitReisender(), is(buchung.getRuckFlughafenMitReisender()));
        assertThat(update.getHandGepaeckReisender(), is(buchung.getHandGepaeckReisender()));
        assertThat(update.getHandGepaeckMitReisender(), is(buchung.getHandGepaeckMitReisender()));
        assertThat(update.getKofferReisender(), is(buchung.getKofferReisender()));
        assertThat(update.getKofferMitReisender(), is(buchung.getKofferMitReisender()));

        assertThat(update.getReisenderId(), is(reisender1.getId()));
        assertThat(update.getMitReisenderId(), is(mitReisender1.getId()));
        assertThat(update.getStatus(), is(buchung.getStatus()));
    }

    @Test
    public void updateBuchung_status() {
    	
    	ChangeStatus status = new ChangeStatus();
    	status.setId(buchung.getId());
    	status.setStatus(Buchungstatus.Bearbeitung.toString());
    	status.setSendMail(true);
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(status)
                        .log().body()
                        .put("/buchungen/changestatus")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Buchung buchung = buchungRepository.findById(id).get();

        assertThat(buchung.getBuchungDatum(), is(buchung.getBuchungDatum()));
        assertThat(buchung.getReiseAngebot().getId(), is(buchung.getReiseAngebot().getId()));
        assertThat(buchung.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
        assertThat(buchung.getReisender().getId(), is(reisender.getId()));
        assertThat(buchung.getStatus(), is(buchung.getStatus()));
    }

    @Test
    public void updateBuchung_status_stornierung() {
    	
    	ChangeStatus status = new ChangeStatus();
    	status.setId(buchung.getId());
    	status.setStatus(Buchungstatus.Bearbeitung.toString());
    	status.setSendMail(true);

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(status)
                        .log().body()
                        .put("/buchungen/changestatus")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Buchung buchung = buchungRepository.findById(id).get();

        assertThat(buchung.getBuchungDatum(), is(buchung.getBuchungDatum()));
        assertThat(buchung.getReiseAngebot().getId(), is(buchung.getReiseAngebot().getId()));
        assertThat(buchung.getBuchungsklasseId(), is(buchung.getBuchungsklasseId()));
        assertThat(buchung.getReisender().getId(), is(reisender.getId()));
        assertThat(buchung.getStatus(), is(buchung.getStatus()));
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

//    @Test
//    public void getBuchungByNumber() {
//
//        BuchungWriteTO create = buildBuchungWriteTO(buchungsklasse.getId(), reiseAngebot.getId());
//
//        UUID id = UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        .body(create)
//                        .log().body()
//                        .post("/buchungen")
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//        Buchung buchung = buchungRepository.findById(id).get();
//
//        UUID id_got = UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        //.body(buchung)
//                        .log().body()
//                        .get("/buchungen/search/" + buchung.getBuchungsnummer())
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//        Buchung buchung_ = buchungRepository.findById(id_got).get();
//
//        assertThat(buchung.getId(), is(buchung_.getId()));
//        assertThat(buchung.getBuchungDatum(), is(buchung_.getBuchungDatum()));
//    }

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
