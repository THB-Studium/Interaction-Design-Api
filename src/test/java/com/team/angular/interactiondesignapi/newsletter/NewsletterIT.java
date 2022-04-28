package com.team.angular.interactiondesignapi.newsletter;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Newsletter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

public class NewsletterIT extends ItBase {

    Newsletter newsletter;

    @BeforeEach
    public void setup() {
        super.setup();

        newsletter = buildNewsletter();
        newsletter = newsletterRepository.save(newsletter);
    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

    @Test
    public void createNewsletter() {
        Newsletter create = buildNewsletter();

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(create)
                        .log().body()
                        .post("/newsletters/subscribe")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Newsletter newsletter = newsletterRepository.findById(id).get();

        assertThat(create.getEmail(), is(newsletter.getEmail()));
        assertThat(create.isStatus(), is(newsletter.isStatus()));
    }

    @Test
    public void createNewsletter_email_exist() {

        Newsletter create = buildNewsletter();
        create.setEmail(newsletter.getEmail());

        String ex = given()
                .contentType(ContentType.JSON)
                .body(create)
                .log().body()
                .when().post("/newsletters/subscribe")
                .then()
                .log().body()
                .statusCode(400)
                .extract().body().path("message");

        Assertions.assertEquals("This email is already subscribed", ex);
    }

    @Test
    public void unsubscribeNewsletter() { //todo: do you compare statusBefore with statusBefore?

        boolean statusBefore = newsletter.isStatus(); // true

        given()
                .contentType(ContentType.JSON)
                .body(newsletter)
                .log().body()
                .put("/newsletters/unsubscribe/" + newsletter.getId())
                .then()
                .log().body()
                .statusCode(200);

        assertThat(statusBefore, is(newsletter.isStatus()));//is(newsletter.isStatus())
    }

    //
    @Test
    public void updateNewsletter() {
        Newsletter create = buildNewsletter();
        UUID id_newsletter = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(create)
                        .log().body()
                        .post("/newsletters/subscribe")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Newsletter newsletter = newsletterRepository.findById(id_newsletter).get();

        Newsletter update = buildNewsletter();
        update.setId(newsletter.getId());
        update.setEmail(create.getEmail());
        update.setStatus(create.isStatus());

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(newsletter)
                        .log().body()
                        .put("/newsletters")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Newsletter newsletter_ = newsletterRepository.findById(id).get();
        assertThat(newsletter_, samePropertyValuesAs(update));
    }

    @Test
    public void listNewsletter() {
        Newsletter newsletter1 = buildNewsletter();
        newsletter1 = newsletterRepository.save(newsletter1);

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .get("/newsletters")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", containsInAnyOrder(newsletter.getId().toString(), newsletter1.getId().toString()))
                .body("email", containsInAnyOrder(newsletter.getEmail(), newsletter1.getEmail()))
                .body("status", containsInAnyOrder(newsletter.isStatus(), newsletter1.isStatus()));
    }

    @Test
    public void deleteNewsletter() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .delete("/newsletters/" + newsletter.getId())
                .then()
                .log().body()
                .statusCode(200);

        assertThat(newsletterRepository.findById(newsletter.getId()).isPresent(), is(false));
    }
}
