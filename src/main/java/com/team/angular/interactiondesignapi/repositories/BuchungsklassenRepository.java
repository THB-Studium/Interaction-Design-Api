package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BuchungsklassenRepository extends JpaRepository<Buchungsklassen, UUID> {
}
