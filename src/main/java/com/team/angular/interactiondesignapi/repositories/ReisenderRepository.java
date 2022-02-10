package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Reisender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReisenderRepository extends JpaRepository<Reisender, UUID> {
    Boolean existsReisenderByTelefonnummer(String telefonnummer);

    Reisender getReisenderByTelefonnummer(String telefonnummer);
}
