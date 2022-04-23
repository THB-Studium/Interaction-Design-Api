package com.team.angular.interactiondesignapi.highlight;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.models.Highlight;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.transfertobjects.hightlight.HighlightReadWriteTO;
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

public class HighlightIT extends ItBase {

    Highlight highlight, highlight1, highlight2;

    HighlightReadWriteTO highlightWrite1;

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

        highlight = buildHighlight(land);
        highlight = highlightRepository.save(highlight);

        highlight1 = buildHighlight(land);
        highlight1 = highlightRepository.save(highlight1);
    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createHighlight() {

        highlightWrite1 = buildHighlightWriteTO(land.getId());


        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
//				.multiPart("highlight", highlightWrite1,"application/json")
//				.multiPart("files", "something123".getBytes())
                        .body(highlightWrite1)
                        .log().body()
                        .post("/highlights")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Highlight highlight = highlightRepository.findById(id).get();

        assertThat(highlightWrite1.getName(), is(highlight.getName()));
        assertThat(highlightWrite1.getDescription(), is(highlight.getDescription()));
        assertThat(highlightWrite1.getBild(), is(highlight.getBild()));
        assertThat(highlightWrite1.getLandId(), is(highlight.getLand().getId()));

    }

    @Test
    public void createHighlight__name_exist() {

        highlightWrite1 = buildHighlightWriteTO(land.getId());
        highlightWrite1.setName(highlight.getName());

        String ex = given()
                .contentType(ContentType.JSON)
                .body(highlightWrite1)
                .log().body()
                .post("/highlights")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals(highlight.getName() + " already exists", ex);
    }

    @Test
    public void updateHighlight__name_exist() {

        highlightWrite1 = buildHighlightWriteTO(land.getId());
        highlightWrite1.setId(highlight.getId());
        highlightWrite1.setName(highlight1.getName());

        String ex = given()
                .contentType(ContentType.JSON)
                .body(highlightWrite1)
                .log().body()
                .put("/highlights")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals(highlight1.getName() + " already exists", ex);
    }

    @Test
    public void listHighlights() {

        given()
                .contentType(ContentType.JSON)
                //.body(create)
                .log().body()
                .get("/highlights")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", containsInAnyOrder(highlight.getId().toString(),
                        highlight1.getId().toString()));

    }

    @Test
    public void updateHighlight() {
        highlightWrite1 = buildHighlightWriteTO(land.getId());
        highlightWrite1.setId(highlight.getId());

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
//				.multiPart("highlight", highlightWrite1,"application/json")
//				.multiPart("files", "something123".getBytes())
                        .body(highlightWrite1)
                        .log().body()
                        .put("/highlights")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Highlight updated_highlight = highlightRepository.findById(id).get();

        assertThat(highlight.getId(), is(updated_highlight.getId()));
        assertThat(highlightWrite1.getName(), is(updated_highlight.getName()));
        assertThat(highlightWrite1.getDescription(), is(updated_highlight.getDescription()));
        //assertThat(highlightWrite1.getBild(), is(updated_highlight.getBild()));
        assertThat(highlightWrite1.getLandId(), is(updated_highlight.getLand().getId()));
    }

    @Test
    public void getHighlight() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        //.body(Highlight)
                        .log().body()
                        .get("/highlights/" + highlight.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Highlight highlight_ = highlightRepository.findById(id).get();

        assertThat(highlight.getId(), is(highlight_.getId()));
        assertThat(highlight.getName(), is(highlight_.getName()));
        assertThat(highlight.getDescription(), is(highlight_.getDescription()));
        assertThat(highlight.getBild(), is(highlight_.getBild()));
        assertThat(highlight.getLand().getId(), is(highlight_.getLand().getId()));
    }

    @Test
    public void deleteHighlight() {

        highlight = buildHighlight(land);
        highlight = highlightRepository.save(highlight);

        given()
                .contentType(ContentType.JSON)
                //.body(Highlight)
                .log().body()
                .delete("/highlights/" + highlight.getId())
                .then()
                .log().body()
                .statusCode(200);

        assertThat(highlightRepository.findById(highlight.getId()).isPresent(), is(false));
    }

}
