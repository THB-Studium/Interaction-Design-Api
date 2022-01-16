package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LandRepository extends JpaRepository<Land, UUID> {
    Boolean existsLandByName(String name);
}
