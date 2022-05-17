package com.team.angular.interactiondesignapi.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import com.team.angular.interactiondesignapi.repositories.ErwartungenRepository;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.Erwartungen2ErwartungenReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadWriteTO;

@Service
public class ErwartungenService {
	private static final Logger log = LoggerFactory.getLogger(ErwartungenService.class);
	@Autowired
	private ErwartungenRepository erwartungenRepository;

	public ErwartungenReadWriteTO getErwartungen(UUID id) {
		Erwartungen erwartungen = findErwartung(id);
		return Erwartungen2ErwartungenReadWriteTO.apply(erwartungen);
	}

	public List<ErwartungenReadListTO> getAll(Integer pageNo, Integer pageSize) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Erwartungen> pagedResult = erwartungenRepository.findAll(paging);

		return Erwartungen2ErwartungenReadListTO.apply(pagedResult.getContent());
	}

	public ErwartungenReadWriteTO addErwartungen(ErwartungenReadWriteTO erwartungen) {
		Erwartungen _erwartungen = new Erwartungen();
		if (erwartungen.getAbenteuer() != 0)
			_erwartungen.setAbenteuer(erwartungen.getAbenteuer());
		if (erwartungen.getEntschleunigung() != 0)
			_erwartungen.setEntschleunigung(erwartungen.getEntschleunigung());
		if (erwartungen.getKonfort() != 0)
			_erwartungen.setKonfort(erwartungen.getKonfort());
		if (erwartungen.getNachhaltigkeit() != 0)
			_erwartungen.setNachhaltigkeit(erwartungen.getNachhaltigkeit());
		if (erwartungen.getSonne_strand() != 0)
			_erwartungen.setSonne_strand(erwartungen.getSonne_strand());
		if (erwartungen.getSicherheit() != 0)
			_erwartungen.setSicherheit(erwartungen.getSicherheit());
		if (erwartungen.getRoad() != 0)
			_erwartungen.setRoad(erwartungen.getRoad());
		return Erwartungen2ErwartungenReadWriteTO.apply(erwartungenRepository.save(_erwartungen));
	}

	public ResponseEntity<?> deleteErwartungen(UUID id) {
		Erwartungen actual = findErwartung(id);

		erwartungenRepository.deleteById(actual.getId());
		log.info("Erwartungen successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public ErwartungenReadListTO updateErwartungen(ErwartungenReadListTO erwartungen) {

		Erwartungen _erwartungen = findErwartung(erwartungen.getId());

		if (erwartungen.getAbenteuer() != 0)
			_erwartungen.setAbenteuer(erwartungen.getAbenteuer());
		if (erwartungen.getEntschleunigung() != 0)
			_erwartungen.setEntschleunigung(erwartungen.getEntschleunigung());
		if (erwartungen.getKonfort() != 0)
			_erwartungen.setKonfort(erwartungen.getKonfort());
		if (erwartungen.getNachhaltigkeit() != 0)
			_erwartungen.setNachhaltigkeit(erwartungen.getNachhaltigkeit());
		if (erwartungen.getSonne_strand() != 0)
			_erwartungen.setSonne_strand(erwartungen.getSonne_strand());
		if (erwartungen.getSicherheit() != 0)
			_erwartungen.setSicherheit(erwartungen.getSicherheit());
		if (erwartungen.getRoad() != 0)
			_erwartungen.setRoad(erwartungen.getRoad());

		erwartungenRepository.save(_erwartungen);

		return Erwartungen2ErwartungenReadListTO.apply(_erwartungen);
	}

	public Erwartungen findErwartung(UUID id) {
		return erwartungenRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Expectation with id" + id));
	}
}
