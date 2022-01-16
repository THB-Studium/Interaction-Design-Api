package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReiseAngebotRepository extends JpaRepository<ReiseAngebot, UUID> {
    Boolean existsReiseAngebotByTitel(String titel);
}