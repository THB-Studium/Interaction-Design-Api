package com.team.angular.interactiondesignapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface Infos_landRepository extends JpaRepository<Infos_land, UUID> {
}
