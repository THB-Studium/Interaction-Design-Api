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
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reiser;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.repositories.ReiserRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reiser.ReiserRead2ReiserTO;

@Service
public class BuchungService {

	@Autowired
	private BuchungRepository buchungRepository;
	
	@Autowired
	private BuchungsklassenRepository buchungsklassenRepository;
	
	@Autowired
	private ReiserRepository reiserRepository;
	
	@Autowired
	private ReiseAngebotRepository reiseAngebotRepository;
	
	@Autowired
	private ReiserService reiserService;

	private static final Logger log = LoggerFactory.getLogger(BuchungService.class);

	public Set<BuchungReadListTO> getAll() {
		return Buchung2BuchungReadListTO.apply(buchungRepository.findAll());
	}

	public BuchungReadTO addBuchung(BuchungWriteTO buchung) {
		
		Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));
		
		Reiser reiser = ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getReiser()));
		
		Reiser mitReiser = ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getMitReiser()));
		
		ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));
			
		Buchung newBuchung = new Buchung();
		newBuchung.setDatum(buchung.getDatum());
		newBuchung.setMitReiserId(mitReiser.getId());
		newBuchung.setBuchungsklasseId(tarif.getId());
		newBuchung.setFlugAhfen(buchung.getFlugAhfen());
		newBuchung.setHandGepaeck(buchung.getHandGepaeck());
		newBuchung.setKoffer(buchung.getKoffer());
		newBuchung.setZahlungMethod(buchung.getZahlungMethod());
		newBuchung.setReiser(reiser);
		newBuchung.setReiseAngebot(ra);

		return Buchung2BuchungReadTO.apply(buchungRepository.save(newBuchung));

	}

	public BuchungReadTO getBuchung(UUID id) {

		Buchung buchung = buchungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

		return Buchung2BuchungReadTO.apply(buchung);
	}

	public BuchungReadTO updateBuchung(BuchungUpdateTO buchung) {
		
		Buchungsklassen tarif = null;
		Reiser reiser = null;
		Reiser mitReiser = null;
		ReiseAngebot ra = null;

		Buchung actual = buchungRepository.findById(buchung.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + buchung.getId()));

		if(buchung.getBuchungsklasseId() != null) {
			tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId()).orElseThrow
				(() -> new ResourceNotFoundException("Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));
			actual.setBuchungsklasseId(tarif.getId());
		}
		
		if(buchung.getReiserId() != null) {
			reiser = reiserRepository.findById(buchung.getReiserId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find Reiser with id: " + buchung.getReiserId()));
			actual.setReiser(reiser);
		}
		
		if(buchung.getMitReiserId() != null) {
			mitReiser = reiserRepository.findById(buchung.getMitReiserId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find MitReiser with id: " + buchung.getMitReiserId()));
			actual.setMitReiserId(mitReiser.getId());
		}
		
		if(buchung.getDatum() != null)
			actual.setDatum(buchung.getDatum());
		if(buchung.getFlugAhfen() != null)
			actual.setFlugAhfen(buchung.getFlugAhfen());
		if(buchung.getHandGepaeck() != null)
			actual.setHandGepaeck(buchung.getHandGepaeck());
		if(buchung.getKoffer() != null)
			actual.setKoffer(buchung.getKoffer());
		if(buchung.getZahlungMethod() != null)
			actual.setZahlungMethod(buchung.getZahlungMethod());
		if(buchung.getReiseAngebotId() != null) {
			ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
					() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));
			actual.setReiseAngebot(ra);
		}

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
