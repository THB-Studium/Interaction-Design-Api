package com.team.angular.interactiondesignapi.feedback;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Feedback;

import io.restassured.http.ContentType;

public class FeedbackIT extends ItBase {
	
	Feedback feedback, feedback1, feedback2;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		
		feedback = buildFeedback();
		feedback = feedbackRepository.save(feedback);
		
		feedback1 = buildFeedback();
		feedback1 = feedbackRepository.save(feedback1);
		
		feedback2 = buildFeedback();
		feedback2 = feedbackRepository.save(feedback2);
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createFeedback() {
		Feedback create = buildFeedback();
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(create)
				.log().body()
				.post("/feedbacks")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Feedback feedback = feedbackRepository.findById(id).get();
		
		assertThat(create.getAutor(), is(feedback.getAutor()));
		assertThat(create.getDescription(), is(feedback.getDescription()));
		
	}
	
	@Test
	public void listFeedbacks() {	
		
			given()
			.contentType(ContentType.JSON)
			//.body(create)
			.log().body()
			.get("/feedbacks")
			.then()
			.log().body()
			.statusCode(200)
			.body("id", containsInAnyOrder(feedback.getId().toString(), feedback1.getId().toString(), feedback2.getId().toString()))
			.body("autor", containsInAnyOrder(feedback.getAutor(), feedback1.getAutor(), feedback2.getAutor()))
			.body("description", containsInAnyOrder(feedback.getDescription(), feedback1.getDescription(), feedback2.getDescription()));
				
	}
	
	@Test
	public void updateFeedback() {
		
		String newAuhtor = UUID.randomUUID().toString();
		feedback.setAutor(newAuhtor);
		feedbackRepository.save(feedback);
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(feedback)
				.log().body()
				.put("/feedbacks")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Feedback feedback = feedbackRepository.findById(id).get();
		
		assertThat(feedback.getId(), is(feedback.getId()));
		assertThat(newAuhtor, is(feedback.getAutor()));
		assertThat(feedback.getDescription(), is(feedback.getDescription()));		
	}
	
//	@Test
//	public void deleteFeedback() {
//		
//		feedback = buildFeedback();
//		feedback = feedbackRepository.save(feedback);
//
//		given()
//		.contentType(ContentType.JSON)
//		//.body(feedback)
//		.log().body()
//		.delete("/feedbacks/", feedback.getId())
//		.then()
//		.log().body()
//		.statusCode(200);
//			
//	}

}
