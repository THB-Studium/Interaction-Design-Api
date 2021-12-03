package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Erwartungen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ErwartungenRepository extends JpaRepository<Erwartungen, UUID> {
}
