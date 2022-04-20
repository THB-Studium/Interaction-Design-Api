package com.team.angular.interactiondesignapi.services;

import com.google.common.io.Files;
import com.lowagie.text.DocumentException;
import com.team.angular.interactiondesignapi.exception.ResourceNotFoundException;
import com.team.angular.interactiondesignapi.models.*;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.BuchungsklassenRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.repositories.ReisenderRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderRead2ReisenderTO;
import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BuchungService {

    private static final Logger log = LoggerFactory.getLogger(BuchungService.class);

    @Value("${template.email.new-booking}")
    private String template_new_booking;

    @Value("${template.email.update-booking}")
    private String template_update_booking;

    @Value("${template.email.html-pdf}")
    private String template;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private BuchungRepository buchungRepository;
    @Autowired
    private BuchungsklassenRepository buchungsklassenRepository;
    @Autowired
    private ReisenderRepository reiserRepository;
    @Autowired
    private ReiseAngebotRepository reiseAngebotRepository;
    @Autowired
    private ReisenderService reiserService;
    @Autowired
    private MailService mailService;

    public List<BuchungReadTO> getAll() {
        return Buchung2BuchungReadTO.apply(buchungRepository.findAll());
    }

    public BuchungReadTO addBuchung(BuchungWriteTO buchung) throws Exception {

        Buchungsklassen tarif = buchungsklassenRepository.findById(buchung.getBuchungsklasseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot find buchungsklasse with id: " + buchung.getBuchungsklasseId()));

        ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));

        // newBuchung
        Buchung newBuchung = new Buchung();

        // check if the Reisender already exists and save when not
        if (reiserRepository.getReisenderByTelefonnummer(buchung.getReisender().getTelefonnummer()) != null) {
            newBuchung.setReisender(
                    reiserRepository.getReisenderByTelefonnummer(buchung.getReisender().getTelefonnummer()));
        } else {
            newBuchung
                    .setReisender(ReisenderRead2ReisenderTO.apply(reiserService.addReisender(buchung.getReisender())));
        }

        // check if the MitReisender already exists and save when not
        if (buchung.getMitReisender() != null) {
            if (reiserRepository.getReisenderByTelefonnummer(buchung.getMitReisender().getTelefonnummer()) != null) {
                newBuchung.setMitReisenderId(buchung.getMitReisender().getId());
            } else {
                newBuchung.setMitReisenderId(
                        ReisenderRead2ReisenderTO.apply(reiserService.addReisender(buchung.getMitReisender())).getId());
            }
        }


        newBuchung.setBuchungsklasseId(tarif.getId());
        newBuchung.setDatum(buchung.getDatum());
        newBuchung.setFlughafen(buchung.getFlughafen());
        newBuchung.setHandGepaeck(buchung.getHandGepaeck());
        newBuchung.setKoffer(buchung.getKoffer());
        newBuchung.setZahlungMethod(buchung.getZahlungMethod());
        if (buchung.getStatus() != null) {
            newBuchung.setStatus(buchung.getStatus());
        } else {
            newBuchung.setStatus(Buchungstatus.Eingegangen);
        }
        
        newBuchung.setReiseAngebot(ra);

        // update freiPlaetze after adding a new Buchung
        if (ra.getFreiPlaetze() > 0) {
            ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);

            if (buchung.getMitReisender() != null) {
                ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);
            }

            reiseAngebotRepository.save(ra);
        } else {
            throw new Exception("The trip is fully booked");
        }

        BuchungReadTO ret = Buchung2BuchungReadTO.apply(buchungRepository.save(newBuchung));

        // props for email template
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", newBuchung.getReisender().getName());
        properties.put("ziel", newBuchung.getReiseAngebot().getLand().getName());

        // build email object
        String[] to = {newBuchung.getReisender().getEmail()};

        // export booking pdf
        byte[] export = exportPdf(ret.getId());

        // data source to write the exported pdf into
        DataSource source = new FileDataSource(ResourceUtils.getFile("classpath:Booking.jrxml"));

        OutputStream sourceOS = source.getOutputStream();
        sourceOS.write(export);

        sendMail(properties, to, "Bestätigung der Reservierung", template_new_booking, source);

        return ret;

    }

    public BuchungReadTO getBuchung(UUID id) {

        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

        return Buchung2BuchungReadTO.apply(buchung);
    }

    public BuchungReadTO updateBuchung(BuchungUpdateTO buchung) throws JRException, URISyntaxException, IOException {

        Buchungsklassen tarif = null;
        Reisender reiser = null;
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
            reiser = reiserRepository.findById(buchung.getReisenderId()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find Reisender with id: " + buchung.getReisenderId()));
            actual.setReisender(reiser);
        }

        if (buchung.getMitReisenderId() != null) {
            mitReisender = reiserRepository.findById(buchung.getMitReisenderId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cannot find MitReisender with id: " + buchung.getMitReisenderId()));
            actual.setMitReisenderId(mitReisender.getId());
        } else if (buchung.getMitReisenderId() == null) {
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

        // send mail on updated status
        //todo: update freiplätze wenn abgeleht!?
        if (buchung.getStatus() != null && buchung.getStatus() != actual.getStatus()) {
            actual.setStatus(buchung.getStatus());
            // template params
            Map<String, Object> properties = new HashMap<>();
            properties.put("name", actual.getReisender().getName());
            properties.put("status", actual.getStatus());
            properties.put("ziel", actual.getReiseAngebot().getLand().getName());

            String[] to = {actual.getReisender().getEmail()};

            // export booking pdf
            byte[] export = exportPdf(actual.getId());

            // data source to write the exported pdf into
            DataSource source = new FileDataSource(ResourceUtils.getFile("classpath:Booking.jrxml"));

            OutputStream sourceOS = source.getOutputStream();
            sourceOS.write(export);

            sendMail(properties, to, "Aktualisierung der Reservierung", template_update_booking, source);
        }

        return Buchung2BuchungReadTO.apply(buchungRepository.save(actual));
    }

    public ResponseEntity<?> removeMitReisender(UUID id) {
        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Buchung with id: " + id));

        buchung.setMitReisenderId(null);
        // buchungRepository.deleteById(buchung.getId());
        buchungRepository.save(buchung);
        log.info("successfully removed");

        // todo: why commented?
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

    // todo: why have we a Email-Service?
    private void sendMail(Map<String, Object> properties, String[] to, String subject, String template,
                          DataSource source) {

        Email email = new Email();
        email.setFrom("keunne.baudoin@yahoo.fr");
        email.setReply(false);
        email.setTo(to);
        email.setSubject(subject);
        email.setTemplate(template);
        email.setProperties(properties);
        if (source != null) {
            try {
                mailService.sendHtmlMessageAttachment(email, source);
                log.info("Successfully sent mail");
            } catch (MessagingException | IOException e) {
                log.error("Could not send the Email. Error -> %s", e.getMessage());
            }
        } else {
            try {
                mailService.sendHtmlMessage(email);
                log.info("Successfully sent mail");
            } catch (MessagingException | IOException e) {
                log.error("Could not send the Email. Error -> %s", e.getMessage());
            }
        }

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

        // since we generate pdfs with html we don't need the jasper approach for the moment
        // print pdf
//		File file = null;


        // template config local
//		file = ResourceUtils.getFile("classpath:Booking.jrxml");
//
//		if (buchung.getMitReisenderId() != null) {
//			file = ResourceUtils.getFile("classpath:Booking_mitReisende.jrxml");
//		}
//
//		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // template config on server
//		JasperReport jasperReport = JasperCompileManager.compileReport(templateLink);
//		
//		if(buchung.getMitReisenderId() != null) {
//			jasperReport = JasperCompileManager.compileReport(templateLink_MitReisende);
//		}

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
        params.put("mit_mitreiser_bool", buchung.getMitReisenderId() != null ? true : false);

        if (buchung.getMitReisenderId() != null) {
            Reisender mitReisender = reiserRepository.findById(buchung.getMitReisenderId()).get();
            params.put("mitReisender_name_vorname", mitReisender.getName() + "  " + mitReisender.getVorname());
            params.put("mitReisender_postanschrift", mitReisender.getAdresse());
            params.put("mitReisender_geburtsdatum", mitReisender.getGeburtsdatum().toString());
            params.put("mitReisender_handynummer", mitReisender.getTelefonnummer());
            params.put("mitReisender_email", mitReisender.getEmail());
            params.put("mitReisender_studiengang", mitReisender.getStudiengang());
            params.put("mitReisender_hochschule", mitReisender.getHochschule());
            params.put("mitReisender_status", mitReisender.getStatus());
            params.put("mitReisender_arbeit_bei", mitReisender.getArbeitBei());
            params.put("mitReisender_schonteilgenommen", mitReisender.isSchonTeilgenommen() ? "Ja" : "Nein");
        }

        params.put("copyright_monat_jahr", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear());

        params.put("reise_datum", buchung.getDatum().toString());
        params.put("buchungsklasse", tarif.getType());
        params.put("zahlungsmethode", buchung.getZahlungMethod().toString());
        params.put("flughafen", buchung.getFlughafen());
        params.put("handgepaeck", buchung.getHandGepaeck().equals("true") ? "ja" : "nein");
        params.put("koffer", buchung.getKoffer().equals("true") ? "ja" : "nein");
        params.put("jahr", "" + LocalDate.now().getYear());
        params.put("buchungsstatus", buchung.getStatus());

        // jasper export not need for the moment
        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

        // export pdf with jasper
        //byte[] export = JasperExportManager.exportReportToPdf(jasperPrint);

        // export pdf with html
        byte[] export_html = generatePdfFile(params, "export.pdf");

        return export_html;
    }

    private byte[] generatePdfFile(Map<String, Object> data, String pdfFileName) throws IOException {
        // thymeleaf context
        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(template, context);
        File file = null;
        try {
            // generate pdf with html template with flying-saucer-pdf
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
            // get the created pdf as file
            file = new File(pdfFileName);
            templateEngine.clearTemplateCache();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } catch (DocumentException e) {
            log.error(e.getMessage(), e);
        }

        // return as byte[]
        return Files.toByteArray(file);
    }

}
