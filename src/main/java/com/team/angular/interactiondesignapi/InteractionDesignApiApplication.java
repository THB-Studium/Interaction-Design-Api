package com.team.angular.interactiondesignapi;

import com.team.angular.interactiondesignapi.models.Admin;
import com.team.angular.interactiondesignapi.services.AdminService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InteractionDesignApiApplication {
    //public class InteractionDesignApiApplication implements CommandLineRunner {

    @Autowired
    AdminService adminService;

    public static void main(String[] args) throws JRException {
        SpringApplication.run(InteractionDesignApiApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception { //todo: add admin; check if exist aber skript?
        adminService.addAdmin(new Admin(null, "superadmin", "superadmin", "superadminkennwort", "superadmin@universitytravelclub.de", null, null, null));
    }*/

}
