package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
