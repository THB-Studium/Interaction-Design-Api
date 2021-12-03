package com.team.angular.interactiondesignapi.services;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Land;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.LandRepository;
import com.team.angular.interactiondesignapi.repositories.ReiserRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;

@Service
public class BuchungService {

	@Autowired
	private BuchungRepository buchungRepository;
	
	@Autowired
	private BuchungsklassenRepository buchungsklassenRepository;
	
	@Autowired
	private ReiserRepository reiserRepository;
	
	@Autowired
	private LandRepository landRepository;

	private static final Logger log = LoggerFactory.getLogger(BuchungService.class);

	public Set<BuchungReadListTO> getAll() {
		return Buchung2BuchungReadListTO.apply(buchungRepository.findAll());
	}

	public BuchungReadTO addBuchung(BuchungWriteTO buchung) {
		
		Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

		Reiser reiser = reiserRepository.findById(buchung.getReiserId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find Reiser with id: " + buchung.getReiserId()));
		
		Land land = landRepository.findById(buchung.getLandId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find Land with id: " + buchung.getLandId()));
		
		Buchung newBuchung = new Buchung();
		newBuchung.setDatum(buchung.getDatum());
		newBuchung.setMitReiser(buchung.getMitReiser());
		newBuchung.setTarif(tarif);
		newBuchung.setFlugAhfen(buchung.getFlugAhfen());
		newBuchung.setHandGepaeck(buchung.getHandGepaeck());
		newBuchung.setKoffer(buchung.getKoffer());
		newBuchung.setZahlungMethod(buchung.getZahlungMethod());
		newBuchung.setReiser(reiser);
		newBuchung.setLand(land);

		return Buchung2BuchungReadTO.apply(buchungRepository.save(newBuchung));

	}

	public BuchungReadTO getBuchung(UUID id) {

		Buchung buchung = buchungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

		return Buchung2BuchungReadTO.apply(buchung);
	}

	public BuchungReadTO updateBuchung(BuchungWriteTO buchung) {

		Buchung actual = buchungRepository.findById(buchung.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + buchung.getId()));

		Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

		Reiser reiser = reiserRepository.findById(buchung.getReiserId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find Reiser with id: " + buchung.getReiserId()));
		
		Land land = landRepository.findById(buchung.getLandId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find Land with id: " + buchung.getLandId()));
		
		actual.setDatum(buchung.getDatum());
		actual.setMitReiser(buchung.getMitReiser());
		actual.setTarif(tarif);
		actual.setFlugAhfen(buchung.getFlugAhfen());
		actual.setHandGepaeck(buchung.getHandGepaeck());
		actual.setKoffer(buchung.getKoffer());
		actual.setZahlungMethod(buchung.getZahlungMethod());
		actual.setReiser(reiser);
		actual.setLand(land);

		return Buchung2BuchungReadTO.apply(buchungRepository.save(actual));
	}

	public ResponseEntity<?> deleteBuchung(UUID id) {
		Buchung actual = buchungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

		buchungRepository.deleteById(actual.getId());
		log.info("successfully delted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

}
