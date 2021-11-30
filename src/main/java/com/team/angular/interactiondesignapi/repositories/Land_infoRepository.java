package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Land_info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Land_infoRepository extends JpaRepository<Land_info, UUID> {
}
