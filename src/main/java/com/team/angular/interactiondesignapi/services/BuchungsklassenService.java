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
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungsklassen2BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.Buchungsklassen2BuchungsklassenReadWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadWriteTO;

@Service
public class BuchungsklassenService {

	private static final Logger log = LoggerFactory.getLogger(BuchungsklassenService.class);

	@Autowired
	private BuchungsklassenRepository buchungsklassenRepository;
	@Autowired
	private ReiseAngebotService reiseAngebotService;

	public BuchungsklassenReadWriteTO getBuchungsklassen(UUID id) {
		Buchungsklassen buchungsklassen = findBuchungsklasse(id);
		return Buchungsklassen2BuchungsklassenReadWriteTO.apply(buchungsklassen);
	}

	public List<BuchungsklassenReadListTO> getAll(Integer pageNo, Integer pageSize) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Buchungsklassen> pagedResult = buchungsklassenRepository.findAll(paging);

		return Buchungsklassen2BuchungsklassenReadListTO.apply(pagedResult.getContent());
	}

	public BuchungsklassenReadWriteTO addBuchungsklassen(BuchungsklassenReadWriteTO buchungsklassen) {
		Buchungsklassen _buchungsklassen = new Buchungsklassen();

		_buchungsklassen.setType(buchungsklassen.getType());

		if (buchungsklassen.getPreis() != 0)
			_buchungsklassen.setPreis(buchungsklassen.getPreis());

		_buchungsklassen
				.setDescription(buchungsklassen.getDescription() != null ? buchungsklassen.getDescription() : null);
		
		if (buchungsklassen.getReiseAngebotId() != null) {
			ReiseAngebot reiseAngebot = reiseAngebotService.findReiseAngebot(buchungsklassen.getReiseAngebotId());
			_buchungsklassen.setReiseAngebot(reiseAngebot);
		}

		return Buchungsklassen2BuchungsklassenReadWriteTO.apply(buchungsklassenRepository.save(_buchungsklassen));
	}

	public ResponseEntity<?> deleteBuchungsklassen(UUID id) {
		Buchungsklassen actual = findBuchungsklasse(id);

		buchungsklassenRepository.deleteById(actual.getId());
		log.info("Buchungsklassen successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public BuchungsklassenReadListTO updateBuchungsklassen(BuchungsklassenReadWriteTO buchungsklassen) {

		Buchungsklassen _buchungsklassen = findBuchungsklasse(buchungsklassen.getId());

		_buchungsklassen.setType(buchungsklassen.getType());

		if (buchungsklassen.getPreis() != 0)
			_buchungsklassen.setPreis(buchungsklassen.getPreis());
		if (!buchungsklassen.getDescription().isEmpty())
			_buchungsklassen.setDescription(buchungsklassen.getDescription());

		buchungsklassenRepository.save(_buchungsklassen);

		return Buchungsklassen2BuchungsklassenReadListTO.apply(_buchungsklassen);
	}

	public Buchungsklassen findBuchungsklasse(UUID id) {
		return buchungsklassenRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Booking class with id" + id));
	}
}
