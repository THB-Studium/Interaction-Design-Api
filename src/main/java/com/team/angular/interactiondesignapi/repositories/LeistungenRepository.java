package com.team.angular.interactiondesignapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.angular.interactiondesignapi.models.Leistungen;

@Repository
public interface LeistungenRepository extends JpaRepository<Leistungen, UUID> {

}
