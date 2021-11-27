package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Infos_land;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Infos_landRepository extends JpaRepository<Infos_land, UUID> {
}
