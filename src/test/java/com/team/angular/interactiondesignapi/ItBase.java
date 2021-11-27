package com.team.angular.interactiondesignapi;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.team.angular.interactiondesignapi.models.Feedback;
import com.team.angular.interactiondesignapi.repositories.FeedbackRepository;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class ItBase {

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	protected MockHttpSession session;

	@Autowired
	protected FeedbackRepository feedbackRepository;

	@BeforeEach
	public void setup() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RestAssuredMockMvc.webAppContextSetup(wac);
	}

	public void cleanup() {
		feedbackRepository.deleteAll();
	}

	protected Feedback buildFeedback() {
		Feedback feedback = new Feedback();

		feedback.setAutor(UUID.randomUUID().toString());
		feedback.setDescription(UUID.randomUUID().toString());

		return feedback;
	}

}
