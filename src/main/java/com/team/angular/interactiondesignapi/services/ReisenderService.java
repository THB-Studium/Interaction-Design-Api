package com.team.angular.interactiondesignapi.services;

import static com.team.angular.interactiondesignapi.config.CompressImage.compressBild;

import java.util.List;
import java.util.UUID;

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

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.repositories.ReisenderRepository;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.Reisender2ReisenderReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.Reisender2ReisenderReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderWriteTO;

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

	public ReisenderReadTO addReisender(ReisenderWriteTO reisender) {

		Reisender newReisender = new Reisender();
		if (!reisenderRepository.existsReisenderByTelefonnummer(reisender.getTelefonnummer())) {
			newReisender.setTelefonnummer(reisender.getTelefonnummer());
		} else {
			throw new ApiRequestException(reisender.getTelefonnummer() + " already exists");
		}

		newReisender.setName(reisender.getName());
		newReisender.setVorname(reisender.getVorname());
		newReisender.setGeburtsdatum(reisender.getGeburtsdatum());
		newReisender.setEmail(reisender.getEmail());
		newReisender.setAdresse(reisender.getAdresse());
		newReisender.setSchonTeilgenommen(reisender.isSchonTeilgenommen());

		if(reisender.getIdentityCard() != null)
			newReisender.setIdentity_card(compressBild(reisender.getIdentityCard()));

		newReisender.setHochschule(reisender.getHochschule() != null ? reisender.getHochschule() : null);

		newReisender.setStudiengang(reisender.getStudiengang() != null ? reisender.getStudiengang() : null);

		newReisender.setStatus(reisender.getStatus() != null ? reisender.getStatus() : null);

		newReisender.setArbeitBei(reisender.getArbeitBei() != null ? reisender.getArbeitBei() : null);

		return Reisender2ReisenderReadTO.apply(reisenderRepository.save(newReisender));

	}

	public ReisenderReadTO updateReisender(ReisenderWriteTO reisender) {

		Reisender actual = findReisender(reisender.getId());

		if (reisender.getTelefonnummer() != null && !reisender.getTelefonnummer().equals(actual.getTelefonnummer())) {
			if (!reisenderRepository.existsReisenderByTelefonnummer(reisender.getTelefonnummer())) {
				actual.setTelefonnummer(reisender.getTelefonnummer());
			} else {
				throw new ApiRequestException(reisender.getTelefonnummer() + " already exists");
			}
		}

		actual.setName(reisender.getName() != null ? reisender.getName() : null);

		actual.setVorname(reisender.getVorname() != null ? reisender.getVorname() : null);

		actual.setGeburtsdatum(reisender.getGeburtsdatum() != null ? reisender.getGeburtsdatum() : null);

		actual.setEmail(reisender.getEmail() != null ? reisender.getEmail() : null);

		actual.setHochschule(reisender.getHochschule() != null ? reisender.getHochschule() : null);

		actual.setAdresse(reisender.getAdresse() != null ? reisender.getAdresse() : null);

		actual.setStudiengang(reisender.getStudiengang() != null ? reisender.getStudiengang() : null);

		actual.setStatus(reisender.getStatus() != null ? reisender.getStatus() : null);

		if(reisender.getIdentityCard() != null)
			actual.setIdentity_card(compressBild(reisender.getIdentityCard()));

		actual.setArbeitBei(reisender.getArbeitBei() != null ? reisender.getArbeitBei() : null);

		actual.setSchonTeilgenommen(
				reisender.isSchonTeilgenommen() != actual.isSchonTeilgenommen() ? reisender.isSchonTeilgenommen()
						: actual.isSchonTeilgenommen());

		return Reisender2ReisenderReadTO.apply(reisenderRepository.save(actual));
	}

	public ResponseEntity<?> deleteReisender(UUID id) {
		Reisender actual = findReisender(id);

		reisenderRepository.deleteById(actual.getId());
		log.info("successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public Reisender findReisender(UUID id) {
		return reisenderRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Traveller with id" + id));
	}

}
