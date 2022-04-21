package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.repositories.ReisenderRepository;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReisenderService {

    private static final Logger log = LoggerFactory.getLogger(ReisenderService.class);

    @Autowired
    private ReisenderRepository reisenderRepository;

    public List<ReisenderReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Reisender> pagedResult = reisenderRepository.findAll(paging);

        return Reisender2ReisenderReadListTO.apply(pagedResult.getContent());
    }

    public ReisenderReadTO getReisender(UUID id) {

        Reisender reisender = reisenderRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Reisender with id: " + id));

        return Reisender2ReisenderReadTO.apply(reisender);
    }

    public ReisenderReadTO addReisender(ReisenderWriteTO Reisender) {

        Reisender newReisender = new Reisender();
        if (!reisenderRepository.existsReisenderByTelefonnummer(Reisender.getTelefonnummer())) {
            newReisender.setTelefonnummer(Reisender.getTelefonnummer());
        } else {
            throw new ApiRequestException(Reisender.getTelefonnummer() + " already exists");
        }

        newReisender.setName(Reisender.getName());
        newReisender.setVorname(Reisender.getVorname());
        newReisender.setGeburtsdatum(Reisender.getGeburtsdatum());
        newReisender.setEmail(Reisender.getEmail());
        newReisender.setAdresse(Reisender.getAdresse());
        newReisender.setSchonTeilgenommen(Reisender.isSchonTeilgenommen());

        if (Reisender.getHochschule() != null)
            newReisender.setHochschule(Reisender.getHochschule());
        if (Reisender.getStudiengang() != null)
            newReisender.setStudiengang(Reisender.getStudiengang());
        if (Reisender.getStatus() != null)
            newReisender.setStatus(Reisender.getStatus());
        if (Reisender.getArbeitBei() != null)
            newReisender.setArbeitBei(Reisender.getArbeitBei());

        return Reisender2ReisenderReadTO.apply(reisenderRepository.save(newReisender));

    }

    public ReisenderReadTO updateReisender(ReisenderWriteTO Reisender) {

        Reisender actual = reisenderRepository.findById(Reisender.getId())
                .orElseThrow(() -> new ApiRequestException("Cannot find Reisender with id: " + Reisender.getId()));

        if (Reisender.getTelefonnummer() != null && !Reisender.getTelefonnummer().equals(actual.getTelefonnummer())) {
            if (!reisenderRepository.existsReisenderByTelefonnummer(Reisender.getTelefonnummer())) {
                actual.setTelefonnummer(Reisender.getTelefonnummer());
            } else {
                throw new ApiRequestException(Reisender.getTelefonnummer() + " already exists");
            }
        }

        if (Reisender.getName() != null)
            actual.setName(Reisender.getName());
        if (Reisender.getVorname() != null)
            actual.setVorname(Reisender.getVorname());
        if (Reisender.getGeburtsdatum() != null)
            actual.setGeburtsdatum(Reisender.getGeburtsdatum());
        if (Reisender.getEmail() != null)
            actual.setEmail(Reisender.getEmail());
        if (Reisender.getHochschule() != null)
            actual.setHochschule(Reisender.getHochschule());
        if (Reisender.getAdresse() != null)
            actual.setAdresse(Reisender.getAdresse());
        if (Reisender.getStudiengang() != null)
            actual.setStudiengang(Reisender.getStudiengang());
        if (Reisender.getStatus() != null)
            actual.setStatus(Reisender.getStatus());
        if (Reisender.getArbeitBei() != null)
            actual.setArbeitBei(Reisender.getArbeitBei());
        if (Reisender.isSchonTeilgenommen() != actual.isSchonTeilgenommen())
            actual.setSchonTeilgenommen(Reisender.isSchonTeilgenommen());

        return Reisender2ReisenderReadTO.apply(reisenderRepository.save(actual));
    }

    public ResponseEntity<?> deleteReisender(UUID id) {
        Reisender actual = reisenderRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Reisender with id: " + id));

        reisenderRepository.deleteById(actual.getId());
        log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }

}
