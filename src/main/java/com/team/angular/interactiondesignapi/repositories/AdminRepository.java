package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin findAdminByName(String name);

    Admin findAdminByEmail(String email);

    Boolean existsAdminByEmail(String email);

    Boolean existsByEmail(String email);
}
