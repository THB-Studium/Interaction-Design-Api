package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.LandInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LandInfoRepository extends JpaRepository<LandInfo, UUID> {
}
