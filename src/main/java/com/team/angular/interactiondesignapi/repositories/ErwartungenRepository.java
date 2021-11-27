package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Erwartungen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ErwartungenRepository extends JpaRepository<Erwartungen, UUID> {
}
