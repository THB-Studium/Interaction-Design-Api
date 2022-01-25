package com.team.angular.interactiondesignapi.repositories;

import com.team.angular.interactiondesignapi.models.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, UUID> {
    boolean existsNewsletterByEmail(String email);

    boolean existsNewsletterByEmailAndIdIsNot(String email, UUID id);
}
