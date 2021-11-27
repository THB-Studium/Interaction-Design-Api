package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HighlightRepository extends JpaRepository<Highlight, UUID>{
}
