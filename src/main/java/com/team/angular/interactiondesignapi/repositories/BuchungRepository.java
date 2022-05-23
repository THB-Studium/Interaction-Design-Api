package com.team.angular.interactiondesignapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.angular.interactiondesignapi.models.Buchung;

@Repository
public interface BuchungRepository extends JpaRepository<Buchung, UUID> {

    Optional<Buchung> findFirstByOrderByNummerDesc();

    Optional<Buchung> findByBuchungsnummer(String buchungsnummer);
}
