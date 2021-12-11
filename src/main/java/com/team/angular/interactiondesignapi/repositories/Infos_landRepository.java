package com.team.angular.interactiondesignapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.angular.interactiondesignapi.models.Land_info;
@Repository
public interface Infos_landRepository extends JpaRepository<Land_info, UUID> {
}
