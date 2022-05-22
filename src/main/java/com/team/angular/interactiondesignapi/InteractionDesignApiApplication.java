package com.team.angular.interactiondesignapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.repositories.AdminRepository;
import com.team.angular.interactiondesignapi.services.AdminService;

import net.sf.jasperreports.engine.JRException;

@SpringBootApplication
public class InteractionDesignApiApplication implements CommandLineRunner {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRepository adminRepository;

	public static void main(String[] args) throws JRException {
		SpringApplication.run(InteractionDesignApiApplication.class, args);
	}

	public void run(String... args) throws Exception {
		if (!adminRepository.existsByEmail("superadmin@universitytravelclub.de")) {
			adminService.addAdmin(new Admin(null, "superadmin", "superadmin", "superadminkennwort",
					"superadmin@universitytravelclub.de", null, null, null));
		}
	}

}
