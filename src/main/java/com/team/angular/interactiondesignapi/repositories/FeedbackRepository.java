package com.team.angular.interactiondesignapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.angular.interactiondesignapi.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

}
