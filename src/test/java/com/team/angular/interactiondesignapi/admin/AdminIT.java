package com.team.angular.interactiondesignapi.admin;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.team.angular.interactiondesignapi.ItBase;
import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.transfertobjects.admin.AdminWriteTO;

import io.restassured.http.ContentType;

public class AdminIT extends ItBase {
	
	Admin admin, admin1;
	
	@BeforeEach
	public void setup() {
		super.setup();
		
		admin = buildAdmin();
		admin = adminRepository.save(admin);
		
		admin1 = buildAdmin();
		admin1 = adminRepository.save(admin1);
		
	}
	
    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

	
	@Test
	public void createAdmin() {
		Admin create = buildAdmin();
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(create)
					.log().body()
					.post("/admins")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Admin admin = adminRepository.findById(id).get();
		
		assertThat(create.getName(), is(admin.getName()));
		assertThat(create.getSurname(), is(admin.getSurname()));
		assertThat(bcryptEncoder.matches(create.getPassword(), admin.getPassword()), is(true));
		assertThat(create.getEmail(), is(admin.getEmail()));
		assertThat("ROLE_ADMIN", is(admin.getRole()));

	}
	
	@Test
	public void createAdmin__email_exist() {
		
		Admin create = buildAdmin();
			  create.setEmail(admin.getEmail());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
						.body(create)
						.log().body()
						.post("/admins")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: Email has already been taken", ex.getLocalizedMessage());
	}
	
	@Test
	public void updateAdmin__email_exist() {
			
		AdminWriteTO update = buildAdminWriteTO();
		update.setId(admin.getId());
		update.setEmail(admin1.getEmail());
		
		Exception ex = Assertions.assertThrows(Exception.class, () -> {
			UUID.fromString(
					given()
						.contentType(ContentType.JSON)
						.body(update)
						.log().body()
						.put("/admins")
						.then()
						.log().body()
						.statusCode(200)
						.extract().body().path("id"));
		});
		
		Assertions.assertEquals("Request processing failed; nested exception is java.lang.Exception: Email has already been taken", ex.getLocalizedMessage());
	}
	
	@Test
	public void listAdmins() {	
		
			given()
				.contentType(ContentType.JSON)
				//.body(create)
				.log().body()
				.get("/admins")
				.then()
				.log().body()
				.statusCode(200)
				.body("id", containsInAnyOrder(admin.getId().toString(), admin1.getId().toString()))
				.body("name", containsInAnyOrder(admin.getName(), admin1.getName()))
				.body("surname", containsInAnyOrder(admin.getSurname(), admin1.getSurname()))
				.body("password", containsInAnyOrder(admin.getPassword(), admin1.getPassword()))
				.body("email", containsInAnyOrder(admin.getEmail(), admin1.getEmail()));
	}
	
	@Test
	public void updateAdmin() {
		
		Admin create = buildAdmin();
		
		UUID id_adm = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(create)
					.log().body()
					.post("/admins")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Admin admin = adminRepository.findById(id_adm).get();
		
		AdminWriteTO update = buildAdminWriteTO();
		update.setId(admin.getId());
		update.setOldPassword(create.getPassword());
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					.body(update)
					.log().body()
					.put("/admins")
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Admin admin_ = adminRepository.findById(id).get();
		
		assertThat(update.getId(), is(admin_.getId()));
		assertThat(update.getName(), is(admin_.getName()));
		assertThat(update.getSurname(), is(admin_.getSurname()));
		assertThat(update.getEmail(), is(admin_.getEmail()));
		
		assertThat(bcryptEncoder.matches(update.getNewPassword(), admin_.getPassword()), is(true));
	}
	
	@Test
	public void getAdmin() {
		
		UUID id = UUID.fromString(
				given()
					.contentType(ContentType.JSON)
					//.body(admin)
					.log().body()
					.get("/admins/"+admin.getId() )
					.then()
					.log().body()
					.statusCode(200)
					.extract().body().path("id"));
		
		Admin admin_ = adminRepository.findById(id).get();
		
		assertThat(admin.getId(), is(admin_.getId()));
		assertThat(admin.getName(), is(admin_.getName()));
		assertThat(admin.getSurname(), is(admin_.getSurname()));
		assertThat(admin.getPassword(), is(admin_.getPassword()));
		assertThat(admin.getEmail(), is(admin_.getEmail()));
	}
	
	@Test
	public void deleteAdmin() {

		given()
			.contentType(ContentType.JSON)
			//.body(admin)
			.log().body()
			.delete("/admins/"+admin.getId())
			.then()
			.log().body()
			.statusCode(200);	
		
		assertThat(adminRepository.findById(admin.getId()).isPresent(), is(false));
	}

}
