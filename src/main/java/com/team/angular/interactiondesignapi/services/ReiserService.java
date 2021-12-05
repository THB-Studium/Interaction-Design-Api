package com.team.angular.interactiondesignapi.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.repositories.ReiserRepository;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.Reiser2ReiserReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.Reiser2ReiserReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserWriteTO;

@Service
public class ReiserService {

	@Autowired
	private ReiserRepository reiserRepository;

	private static final Logger log = LoggerFactory.getLogger(ReiserService.class);

	public List<ReiserReadListTO> getAll() {
		return Reiser2ReiserReadListTO.apply(reiserRepository.findAll());
	}

	public ReiserReadTO addReiser(ReiserWriteTO reiser) {

		Reiser newReiser = new Reiser();
		newReiser.setName(reiser.getName());
		newReiser.setVorname(reiser.getVorname());
		newReiser.setGeburtsdatum(reiser.getGeburtsdatum());
		newReiser.setTelefonnummer(reiser.getTelefonnummer());
		newReiser.setEmail(reiser.getEmail());
		newReiser.setHochschule(reiser.getHochschule());
		newReiser.setAdresse(reiser.getAdresse());
		newReiser.setStudiengang(reiser.getStudiengang());
		newReiser.setArbeitBei(reiser.getArbeitBei());
		newReiser.setSchonTeilgenommen(reiser.isSchonTeilgenommen());
		//newReiser.setBuchungen(new HashSet<>());

		return Reiser2ReiserReadTO.apply(reiserRepository.save(newReiser));

	}

	public ReiserReadTO getReiser(UUID id) {

		Reiser reiser = reiserRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Reiser with id: " + id));

		return Reiser2ReiserReadTO.apply(reiser);
	}

	public ReiserReadTO updateReiser(ReiserWriteTO reiser) {

		Reiser actual = reiserRepository.findById(reiser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Reiser with id: " + reiser.getId()));

		if (reiser.getName() != null)
			actual.setName(reiser.getName());
		if (reiser.getVorname() != null)
			actual.setVorname(reiser.getVorname());
		if (reiser.getGeburtsdatum() != null)
			actual.setGeburtsdatum(reiser.getGeburtsdatum());
		if (reiser.getTelefonnummer() != actual.getTelefonnummer())
			actual.setTelefonnummer(reiser.getTelefonnummer());
		if (reiser.getEmail() != null)
			actual.setEmail(reiser.getEmail());
		if (reiser.getHochschule() != null)
			actual.setHochschule(reiser.getHochschule());
		if (reiser.getAdresse() != null)
			actual.setAdresse(reiser.getAdresse());
		if (reiser.getStudiengang() != null)
			actual.setStudiengang(reiser.getStudiengang());
		if (reiser.getArbeitBei() != null)
			actual.setArbeitBei(reiser.getArbeitBei());
		if (reiser.isSchonTeilgenommen() != actual.isSchonTeilgenommen())
			actual.setSchonTeilgenommen(reiser.isSchonTeilgenommen());

		return Reiser2ReiserReadTO.apply(reiserRepository.save(actual));
	}

	public ResponseEntity<?> deleteReiser(UUID id) {
		Reiser actual = reiserRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Reiser with id: " + id));

		reiserRepository.deleteById(actual.getId());
		log.info("successfully delted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

}
