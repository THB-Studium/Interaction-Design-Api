package com.team.angular.interactiondesignapi.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class BuchungService {
	
	@Value("${template.link}")
	private String templateLink;
	
	@Value("${template.linkMitReisende}")
	private String templateLink_MitReisende;

	private static final Logger log = LoggerFactory.getLogger(BuchungService.class);
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
	@Autowired


	public List<BuchungReadTO> getAll() {
		return Buchung2BuchungReadTO.apply(buchungRepository.findAll());
	}

	public BuchungReadTO addBuchung(BuchungWriteTO buchung) throws Exception {

		Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

		// Reiser reiser = buchung.getReiser();
		// Reiser mitReiser =
		// ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getMitReiser()));

		ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
				() -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));

		Buchung newBuchung = new Buchung();
		newBuchung.setDatum(buchung.getDatum());

		// check if the MitReiser already exists and save when not
		if (buchung.getMitReiser() != null) {
			if (reiserRepository.getReisersByTelefonnummer(buchung.getMitReiser().getTelefonnummer()) != null) {
				newBuchung.setMitReiserId(buchung.getMitReiser().getId());
			} else {
				newBuchung.setMitReiserId(
						ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getMitReiser())).getId());
			}
		}

		newBuchung.setBuchungsklasseId(tarif.getId());
		newBuchung.setFlughafen(buchung.getFlughafen());
		newBuchung.setHandGepaeck(buchung.getHandGepaeck());
		newBuchung.setKoffer(buchung.getKoffer());
		newBuchung.setZahlungMethod(buchung.getZahlungMethod());

		// check if the Reiser already exists and save when not
		if (reiserRepository.getReisersByTelefonnummer(buchung.getReiser().getTelefonnummer()) != null) {
			newBuchung.setReiser(reiserRepository.getReisersByTelefonnummer(buchung.getReiser().getTelefonnummer()));
		} else {
			newBuchung.setReiser(ReiserRead2ReiserTO.apply(reiserService.addReiser(buchung.getReiser())));
		}

		newBuchung.setReiseAngebot(ra);

		// update freiPlaetze after adding a new Buchung
		if (ra.getFreiPlaetze() > 0) {
			ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);

			if (buchung.getMitReiser() != null) {
				ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);
			}

			reiseAngebotRepository.save(ra);
		} else {
			throw new Exception("The trip is fully booked");
		}

		return Buchung2BuchungReadTO.apply(buchungRepository.save(newBuchung));

	}

	public byte[] exportPdf(UUID id) throws JRException, URISyntaxException, IOException {
		
		

		Buchung buchung = buchungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

		Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

		ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebot().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cannot find ReiseAngebot with id" + buchung.getReiseAngebot().getId()));

		System.out.println(1234234);
		// print pdf
//		File file = null;
//		
//		file = ResourceUtils.getFile("classpath:Booking.jrxml");
//		
//		if(buchung.getMitReiserId() != null) {
//			file = ResourceUtils.getFile("classpath:Booking_mitReisende.jrxml");
//		}

		JasperReport jasperReport = JasperCompileManager.compileReport(templateLink);
		
		if(buchung.getMitReiserId() != null) {
			jasperReport = JasperCompileManager.compileReport(templateLink_MitReisende);
		}
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("ziel", ra.getLand() != null ? ra.getLand().getName() : ra.getTitel());
		params.put("start_end", ra.getStartDatum().getDayOfMonth() + "." + ra.getStartDatum().getMonth().toString()
				+ " - " + ra.getEndDatum().toString());
		params.put("name_vorname", buchung.getReiser().getName() + "  " + buchung.getReiser().getVorname());
		params.put("postanschrift", buchung.getReiser().getAdresse());
		params.put("geburtsdatum", buchung.getReiser().getGeburtsdatum().toString());
		params.put("handynummer", buchung.getReiser().getTelefonnummer());
		params.put("email", buchung.getReiser().getEmail());
		params.put("studiengang", buchung.getReiser().getStudiengang());
		params.put("hochschule", buchung.getReiser().getHochschule());
		params.put("status", buchung.getReiser().getStatus());
		params.put("arbeit_bei", buchung.getReiser().getArbeitBei());
		params.put("schonteilgenommen", buchung.getReiser().isSchonTeilgenommen() ? "Ja" : "Nein");
		params.put("mit_mitreiser", buchung.getMitReiserId() != null ? "Ja" : "Nein");
		
		
		if(buchung.getMitReiserId() != null) {
			Reiser mitReiser = reiserRepository.findById(buchung.getMitReiserId()).get();
			params.put("mitReiser_name_vorname", mitReiser.getName() + "  " + mitReiser.getVorname());
			params.put("mitReiser_postanschrift", mitReiser.getAdresse());
			params.put("mitReiser_geburtsdatum", mitReiser.getGeburtsdatum().toString());
			params.put("mitReiser_handynummer", mitReiser.getTelefonnummer());
			params.put("mitReiser_email", mitReiser.getEmail());
			params.put("mitReiser_studiengang", mitReiser.getStudiengang());
			params.put("mitReiser_hochschule", mitReiser.getHochschule());
			params.put("mitReiser_status", mitReiser.getStatus());
			params.put("mitReiser_arbeit_bei", mitReiser.getArbeitBei());
			params.put("mitReiser_schonteilgenommen", mitReiser.isSchonTeilgenommen() ? "Ja" : "Nein");
		}
		
		params.put("copyright_monat_jahr", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear());

		params.put("reise_datum", buchung.getDatum().toString());
		params.put("buchungsklasse", tarif.getType());
		params.put("zahlungsmethode", buchung.getZahlungMethod().toString());
		params.put("flughafen", buchung.getFlughafen());
		params.put("handgepaeck", buchung.getHandGepaeck().equals("true") ? "ja" : "nein");
		params.put("koffer", buchung.getKoffer().equals("true") ? "ja" : "nein");
		params.put("jahr", "" + LocalDate.now().getYear());

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

		byte[] export = JasperExportManager.exportReportToPdf(jasperPrint);

		return export;
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

		if (buchung.getBuchungsklasseId() != null) {
			tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));
			actual.setBuchungsklasseId(tarif.getId());
		}

		if (buchung.getReiserId() != null) {
			reiser = reiserRepository.findById(buchung.getReiserId()).orElseThrow(
					() -> new ResourceNotFoundException("Cannot find Reiser with id: " + buchung.getReiserId()));
			actual.setReiser(reiser);
		}

		if (buchung.getMitReiserId() != null) {
			mitReiser = reiserRepository.findById(buchung.getMitReiserId()).orElseThrow(
					() -> new ResourceNotFoundException("Cannot find MitReiser with id: " + buchung.getMitReiserId()));
			actual.setMitReiserId(mitReiser.getId());
		} else if (buchung.getMitReiserId() == null) {
			actual.setMitReiserId(null);
		}

		if (buchung.getDatum() != null)
			actual.setDatum(buchung.getDatum());
		if (buchung.getFlughafen() != null)
			actual.setFlughafen(buchung.getFlughafen());
		if (buchung.getHandGepaeck() != null)
			actual.setHandGepaeck(buchung.getHandGepaeck());
		if (buchung.getKoffer() != null)
			actual.setKoffer(buchung.getKoffer());
		if (buchung.getZahlungMethod() != null)
			actual.setZahlungMethod(buchung.getZahlungMethod());
		if (buchung.getReiseAngebotId() != null) {
			ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));
			actual.setReiseAngebot(ra);
		}

		return Buchung2BuchungReadTO.apply(buchungRepository.save(actual));
	}

	public ResponseEntity<?> removeMitReiser(UUID id) {
		Buchung buchung = buchungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

		buchung.setMitReiserId(null);
		// buchungRepository.deleteById(buchung.getId());
		buchungRepository.save(buchung);

		/*
		 * //update freiPlaetze after deleting a new Buchung ReiseAngebot ra =
		 * buchung.getReiseAngebot();
		 * ra.setFreiPlaetze(buchung.getReiseAngebot().getFreiPlaetze() + 1);
		 * reiseAngebotRepository.save(ra);
		 * 
		 * log.info("successfully deleted");
		 */

		return new ResponseEntity<>("Mitreiser Successfully deleted", HttpStatus.OK);
	}

	public ResponseEntity<?> deleteBuchung(UUID id) {
		Buchung buchung = buchungRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

		buchungRepository.deleteById(buchung.getId());

		// update freiPlaetze after deleting a new Buchung
		ReiseAngebot ra = buchung.getReiseAngebot();
		ra.setFreiPlaetze(buchung.getReiseAngebot().getFreiPlaetze() + 1);
		reiseAngebotRepository.save(ra);

		log.info("successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

}
