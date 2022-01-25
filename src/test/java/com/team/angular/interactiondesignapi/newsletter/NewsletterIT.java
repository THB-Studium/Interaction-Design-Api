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
import static org.hamcrest.Matchers.is;

public class NewsletterIT extends ItBase {

    Newsletter newsletter, newsletter1, newsletter2;

    @BeforeEach
    public void setup() {
        super.setup();

        newsletter = buildNewsletter();
        newsletter = newsletterRepository.save(newsletter);
        //, newsletter1, newsletter2;
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

        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            UUID.fromString(
                    given()
                            .contentType(ContentType.JSON)
                            .body(create)
                            .log().body()
                            .post("/newsletters/subscribe")
                            .then()
                            .log().body()
                            .statusCode(200)
                            .extract().body().path("id"));
        });

        Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: This email is already subscribed", ex.getLocalizedMessage());
    }


    //test email not null
    //test email correct

    //unsubcribe

    //update
    //delete
}
