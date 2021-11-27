package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuchungsklassenRepository extends JpaRepository<Buchungsklassen, UUID> {
}
