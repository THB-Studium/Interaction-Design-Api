package com.team.angular.interactiondesignapi.services;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
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

	@Autowired
	private BuchungRepository buchungRepository;

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
		newReiser.setBuchungen(new HashSet<>());

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

		actual.setName(reiser.getName());
		actual.setVorname(reiser.getVorname());
		actual.setGeburtsdatum(reiser.getGeburtsdatum());
		actual.setTelefonnummer(reiser.getTelefonnummer());
		actual.setEmail(reiser.getEmail());
		actual.setHochschule(reiser.getHochschule());
		actual.setAdresse(reiser.getAdresse());
		actual.setStudiengang(reiser.getStudiengang());
		actual.setArbeitBei(reiser.getArbeitBei());
		actual.setSchonTeilgenommen(reiser.isSchonTeilgenommen());

		if (reiser.getBuchungIds().size() > 0) {
			reiser.getBuchungIds().forEach(id -> {
				Buchung buchung = buchungRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

				actual.getBuchungen().add(buchung);

			});
		}

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
