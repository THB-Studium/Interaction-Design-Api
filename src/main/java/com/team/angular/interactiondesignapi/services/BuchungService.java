package com.team.angular.interactiondesignapi.services;

import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.repositories.ReisenderRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderRead2ReisenderTO;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BuchungService {

    private static final Logger log = LoggerFactory.getLogger(BuchungService.class);
    @Value("${template.link}")
    private String templateLink;
    @Autowired
    private BuchungRepository buchungRepository;
    @Autowired
    private BuchungsklassenRepository buchungsklassenRepository;
    @Autowired
    private ReisenderRepository reisenderRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;
    @Autowired
    private ReisenderService reisenderService;
    @Autowired
    private ReiseAngebotService reiseAngebotService;
    @Autowired
    private ResourceLoader resourceLoader;

    public List<BuchungReadTO> getAll() {
        return Buchung2BuchungReadTO.apply(buchungRepository.findAll());
    }

    public BuchungReadTO addBuchung(BuchungWriteTO buchung) throws Exception {

        Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

        // Reisender reisender = buchung.getReisender();
        // Reisender mitReiser =
        // ReisenderRead2ReisenderTO.apply(reiserService.addReiser(buchung.getMitReiser()));

        ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));

        Buchung newBuchung = new Buchung();
        newBuchung.setDatum(buchung.getDatum());

        // check if the MitReiser already exists and save when not
        if (buchung.getMitReiser() != null) {
            if (reisenderRepository.getReisenderByTelefonnummer(buchung.getMitReiser().getTelefonnummer()) != null) {
                newBuchung.setMitReisenderId(buchung.getMitReiser().getId());
            } else {
                newBuchung.setMitReisenderId(
                        ReisenderRead2ReisenderTO.apply(reisenderService.addReisender(buchung.getMitReiser())).getId());
            }
        }

        newBuchung.setBuchungsklasseId(tarif.getId());
        newBuchung.setFlughafen(buchung.getFlughafen());
        newBuchung.setHandGepaeck(buchung.getHandGepaeck());
        newBuchung.setKoffer(buchung.getKoffer());
        newBuchung.setZahlungMethod(buchung.getZahlungMethod());

        // check if the Reisender already exists and save when not
        if (reisenderRepository.getReisenderByTelefonnummer(buchung.getReisender().getTelefonnummer()) != null) {
            newBuchung.setReisender(reisenderRepository.getReisenderByTelefonnummer(buchung.getReisender().getTelefonnummer()));
        } else {
            newBuchung.setReisender(ReisenderRead2ReisenderTO.apply(reisenderService.addReisender(buchung.getReisender())));
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

        // print pdf
        //File file = null;
        // file = ResourceUtils.getFile("classpath:Booking.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(templateLink);

        Map<String, Object> params = new HashMap<>();
        params.put("ziel", ra.getLand() != null ? ra.getLand().getName() : ra.getTitel());
        params.put("start_end", ra.getStartDatum().getDayOfMonth() + "." + ra.getStartDatum().getMonth().toString()
                + " - " + ra.getEndDatum().toString());
        params.put("name_vorname", buchung.getReisender().getName() + "  " + buchung.getReisender().getVorname());
        params.put("postanschrift", buchung.getReisender().getAdresse());
        params.put("geburtsdatum", buchung.getReisender().getGeburtsdatum().toString());
        params.put("handynummer", buchung.getReisender().getTelefonnummer());
        params.put("email", buchung.getReisender().getEmail());
        params.put("studiengang", buchung.getReisender().getStudiengang());
        params.put("hochschule", buchung.getReisender().getHochschule());
        params.put("status", buchung.getReisender().getStatus());
        params.put("arbeit_bei", buchung.getReisender().getArbeitBei());
        params.put("schonteilgenommen", buchung.getReisender().isSchonTeilgenommen() ? "Ja" : "Nein");
        params.put("mit_mitreiser", buchung.getMitReisenderId() != null ? "Ja" : "Nein");
        params.put("copyright_monat_jahr", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear());

        params.put("reise_datum", buchung.getDatum().toString());
        params.put("buchungsklasse", tarif.getType());
        params.put("zahlungsmethode", buchung.getZahlungMethod().toString());
        params.put("flughafen", buchung.getFlughafen());
        params.put("handgepaeck", buchung.getHandGepaeck());
        params.put("koffer", buchung.getKoffer());
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
        Reisender reisender = null;
        Reisender mitReisender = null;
        ReiseAngebot ra = null;

        Buchung actual = buchungRepository.findById(buchung.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + buchung.getId()));

        if (buchung.getBuchungsklasseId() != null) {
            tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));
            actual.setBuchungsklasseId(tarif.getId());
        }

        if (buchung.getReisenderId() != null) {
            reisender = reisenderRepository.findById(buchung.getReisenderId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find Reisender with id: " + buchung.getReisenderId()));
            actual.setReisender(reisender);
        }

        if (buchung.getMitReisenderId() != null) {
            mitReisender = reisenderRepository.findById(buchung.getMitReisenderId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find MitReiser with id: " + buchung.getMitReisenderId()));
            actual.setMitReisenderId(mitReisender.getId());
        } else {
            actual.setMitReisenderId(null);
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

        buchung.setMitReisenderId(null);
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
