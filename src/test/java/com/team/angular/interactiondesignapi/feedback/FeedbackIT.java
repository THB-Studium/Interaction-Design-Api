package com.team.angular.interactiondesignapi.feedback;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.Feedback2FeedbackWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.feedback.FeedbackWriteTO;

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
		FeedbackWriteTO create = buildFeedbackWriteTO();
		
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
		
		FeedbackWriteTO feedb_ = Feedback2FeedbackWriteTO.apply(feedback);
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(feedb_)
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
	
	@Test
	public void updateFeedback_Bild_Description_Null() {
		
		feedback1 = buildFeedback();
		feedback1.setId(feedback.getId());
		feedback1.setBild(null);
		feedback1.setDescription(null);
		
		FeedbackWriteTO feedb_ = Feedback2FeedbackWriteTO.apply(feedback1);
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				.body(feedb_)
				.log().body()
				.put("/feedbacks")
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Feedback feedback_ = feedbackRepository.findById(id).get();
		
		assertThat(feedback.getId(), is(feedback_.getId()));
		assertThat(feedback.getAutor(), is(feedback_.getAutor()));
		assertThat(feedback.getDescription(), is(feedback_.getDescription()));
		assertThat(feedback.isVeroefentlich(), is(feedback_.isVeroefentlich()));
	}
	
	@Test
	public void getFeedback() {
		
		UUID id = UUID.fromString(
				given()
				.contentType(ContentType.JSON)
				//.body(feedback)
				.log().body()
				.get("/feedbacks/"+feedback.getId() )
				.then()
				.log().body()
				.statusCode(200)
				.extract().body().path("id"));
		
		Feedback feedback_ = feedbackRepository.findById(id).get();
		
		assertThat(feedback.getId(), is(feedback_.getId()));
		assertThat(feedback.getAutor(), is(feedback_.getAutor()));
		assertThat(feedback.getDescription(), is(feedback_.getDescription()));		
	}
	
	@Test
	public void deleteFeedback() {
		
		feedback = buildFeedback();
		feedback = feedbackRepository.save(feedback);

		given()
		.contentType(ContentType.JSON)
		//.body(feedback)
		.log().body()
		.delete("/feedbacks/"+feedback.getId())
		.then()
		.log().body()
		.statusCode(200);

		assertThat(feedbackRepository.findById(feedback.getId()).isPresent(), is(false));
	}

}
