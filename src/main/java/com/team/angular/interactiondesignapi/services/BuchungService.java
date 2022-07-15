package com.team.angular.interactiondesignapi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.common.io.Files;
import com.lowagie.text.DocumentException;
import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.team.angular.interactiondesignapi.models.Buchung;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Buchungstatus;
import com.team.angular.interactiondesignapi.models.Email;
import com.team.angular.interactiondesignapi.models.ReiseAngebot;
import com.team.angular.interactiondesignapi.models.Reisender;
import com.team.angular.interactiondesignapi.repositories.BuchungRepository;
import com.team.angular.interactiondesignapi.repositories.ReiseAngebotRepository;
import com.team.angular.interactiondesignapi.repositories.ReisenderRepository;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.Buchung2BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungUpdateTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungWriteTO;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.ChangeStatus;
import com.team.angular.interactiondesignapi.transfertobjects.reisender.ReisenderRead2ReisenderTO;

import net.sf.jasperreports.engine.JRException;

@Service
public class BuchungService {

	private static final Logger log = LoggerFactory.getLogger(BuchungService.class);

	/*
	 * @Value("${template.link}") private String templateLink;
	 *
	 * @Value("${template.linkMitReisende}") private String
	 * templateLink_MitReisende;
	 */
	// File resource = new
	// ClassPathResource("templates/Booking_template/Booking_mitReisende.jrxml").getFile();
	// String text = new String(Files.readAllBytes(resource.toPath())));

	@Value("template.forStream")
	private String templateLink;

	@Value("${template.email.new-booking}")
	private String template_new_booking;

	@Value("${template.email.update-booking}")
	private String template_update_booking;

	@Value("${template.email.delete-booking}")
	private String template_delete_booking;

	@Value("${template.email.html-pdf}")
	private String template;

	@Value("${template.email.from}")
	private String from;

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private BuchungRepository buchungRepository;
	@Autowired
	private BuchungsklassenService buchungsklassenService;
	@Autowired
	private ReisenderRepository reisenderRepository;
	@Autowired
	private ReiseAngebotRepository reiseAngebotRepository;
	@Autowired
	private ReiseAngebotService reiseAngebotService;
	@Autowired
	private ReisenderService reisenderService;
	@Autowired
	private MailService mailService;

	public List<BuchungReadTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Buchung> pagedResult = buchungRepository.findAll(paging);

		return Buchung2BuchungReadTO.apply(pagedResult.getContent());
	}

	public BuchungReadTO getBuchung(UUID id) {

		Buchung buchung = findBuchung(id);

		return Buchung2BuchungReadTO.apply(buchung);
	}

	public BuchungReadTO getBuchungByBuchungsnummer(String nummer) {

		Optional<Buchung> buchung = buchungRepository.findByBuchungsnummer(nummer);

		if (buchung != null) {
			return Buchung2BuchungReadTO.apply(buchung.get());
		} else {
			throw new ApiRequestException("Cannot find Booking with Buchungsnummer" + nummer);
		}

	}

	public BuchungReadTO addBuchung(BuchungWriteTO buchung) throws Exception {

		// for building the new Buchung
		Buchung newBuchung = new Buchung();

		// get the tarif and set
		Buchungsklassen tarif = buchungsklassenService.findBuchungsklasse(buchung.getBuchungsklasseId());
		newBuchung.setBuchungsklasseId(tarif.getId());

		// get the ReiseAngebot and set
		ReiseAngebot ra = reiseAngebotRepository.findById(buchung.getReiseAngebotId()).orElseThrow(
				() -> new ApiRequestException("Cannot find ReiseAngebot with id" + buchung.getReiseAngebotId()));
		newBuchung.setReiseAngebot(ra);

		// check if the Reisender already exists and save when not

		if (reisenderRepository.getReisenderByTelefonnummer(buchung.getReisender().getTelefonnummer()) != null) {
			newBuchung.setReisender(
					reisenderRepository.getReisenderByTelefonnummer(buchung.getReisender().getTelefonnummer()));
		} else {
			newBuchung.setReisender(
					ReisenderRead2ReisenderTO.apply(reisenderService.addReisender(buchung.getReisender())));
		}

		// check if the MitReisender already exists and save when not
		if (buchung.getMitReisender() != null) {
			if (reisenderRepository.getReisenderByTelefonnummer(buchung.getMitReisender().getTelefonnummer()) != null) {
				newBuchung.setMitReisenderId(buchung.getMitReisender().getId());
			} else {
				newBuchung.setMitReisenderId(ReisenderRead2ReisenderTO
						.apply(reisenderService.addReisender(buchung.getMitReisender())).getId());
			}
		}

		newBuchung.setBuchungDatum(LocalDate.now());

		newBuchung.setAbFlughafenReisender(buchung.getAbFlughafenReisender());
		newBuchung.setAbFlughafenMitReisender(buchung.getAbFlughafenMitReisender());

		newBuchung.setRuckFlughafenReisender(buchung.getRuckFlughafenReisender());
		newBuchung.setRuckFlughafenMitReisender(buchung.getRuckFlughafenMitReisender());

		newBuchung.setHandGepaeckReisender(buchung.getHandGepaeckReisender());
		newBuchung.setHandGepaeckMitReisender(buchung.getHandGepaeckMitReisender());

		newBuchung.setKofferReisender(buchung.getKofferReisender());
		newBuchung.setKofferMitReisender(buchung.getKofferMitReisender());

		newBuchung.setZahlungMethod(buchung.getZahlungMethod());

		newBuchung.setStatus(Buchungstatus.Eingegangen);

		// update freiPlaetze after adding a new Buchung
		if (ra.getFreiPlaetze() > 0) {
			ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);

			if (buchung.getMitReisender() != null) {
				ra.setFreiPlaetze(ra.getFreiPlaetze() - 1);
			}

			reiseAngebotRepository.save(ra);
		} else {
			throw new ApiRequestException("The trip is fully booked");
		}

		// Buchungsnummer
		Optional<Buchung> lastBuchung = buchungRepository.findFirstByOrderByNummerDesc();
		int lastBuchungsnummer = 0;
		if (lastBuchung.isPresent()) {
			lastBuchungsnummer = lastBuchung.get().getNummer() + 1;
		}

		newBuchung.setNummer(lastBuchungsnummer);

		newBuchung.setBuchungsnummer(createBuchungsnummer(ra.getLand().getName(), buchung.getReisender().getName(),
				buchung.getReisender().getVorname(), lastBuchungsnummer));

		newBuchung.setNummer(lastBuchungsnummer);

		newBuchung.setBuchungsnummer(createBuchungsnummer(ra.getLand().getName(), buchung.getReisender().getName(),
				buchung.getReisender().getVorname(), lastBuchungsnummer));

		// save Buchung
		newBuchung = buchungRepository.save(newBuchung);
		BuchungReadTO savedBuchung = Buchung2BuchungReadTO.apply(newBuchung);

		// props for email template
		Map<String, Object> properties = getBookingMetadata(newBuchung.getId());
		properties.put("name", newBuchung.getReisender().getName());

		// build email object
		String[] to = { newBuchung.getReisender().getEmail() };

		// export booking pdf
		byte[] export = exportPdf(savedBuchung.getId());

		// data source to write the exported pdf into
		DataSource source = new FileDataSource(ResourceUtils.getFile(templateLink));
		OutputStream sourceOS = source.getOutputStream();
		sourceOS.write(export);

		// send mail
		sendMail(properties, to, "Bestätigung der Reservierung", template_new_booking, source, newBuchung);

		return savedBuchung;
	}

	public BuchungReadTO updateBuchung(BuchungUpdateTO buchung) {

		Buchung actual = findBuchung(buchung.getId());

		if (buchung.getBuchungsklasseId() != null) {
			Buchungsklassen tarif = buchungsklassenService.findBuchungsklasse(buchung.getBuchungsklasseId());
			actual.setBuchungsklasseId(tarif.getId());
		}

		if (buchung.getReisenderId() != null) {
			Reisender reiser = reisenderService.findReisender(buchung.getReisenderId());
			actual.setReisender(reiser);
		}

		if (buchung.getMitReisenderId() != null) {
			Reisender mitReisender = reisenderService.findReisender(buchung.getMitReisenderId());
			actual.setMitReisenderId(mitReisender.getId());
		} else {

			actual.setMitReisenderId(null);

			ReiseAngebot ra = reiseAngebotService.findReiseAngebot(buchung.getReiseAngebotId());
			ra.setFreiPlaetze(ra.getFreiPlaetze() + 1);
			reiseAngebotRepository.save(ra);

		}

		actual.setBuchungDatum(buchung.getBuchungDatum());

		actual.setAbFlughafenReisender(buchung.getAbFlughafenReisender());
		actual.setAbFlughafenMitReisender(buchung.getAbFlughafenMitReisender());

		actual.setRuckFlughafenReisender(buchung.getRuckFlughafenReisender());
		actual.setRuckFlughafenMitReisender(buchung.getRuckFlughafenMitReisender());

		actual.setHandGepaeckReisender(buchung.getHandGepaeckReisender());
		actual.setHandGepaeckMitReisender(buchung.getHandGepaeckMitReisender());

		actual.setKofferReisender(buchung.getKofferReisender());
		actual.setKofferMitReisender(buchung.getKofferMitReisender());

		actual.setZahlungMethod(buchung.getZahlungMethod());

		if (buchung.getReiseAngebotId() != null) {
			ReiseAngebot ra = reiseAngebotService.findReiseAngebot(buchung.getReiseAngebotId());
			actual.setReiseAngebot(ra);
		}

		if (buchung.getStatus() != null) {
			// check the new status
			// Buchungstatus toUpdate = null;
			try {
				actual.setStatus(buchung.getStatus());
			} catch (Exception e) {
				throw new ApiRequestException("Status is invalid");
			}
		}

		// save
		actual = buchungRepository.save(actual);

		// props for email template
		Map<String, Object> properties = new HashMap<>();
		try {
			properties = getBookingMetadata(actual.getId());
		} catch (Exception e) {
			throw new ApiRequestException("Error during exporting booking metadata: " + e.getMessage());
		}
		properties.put("name", actual.getReisender().getName());
		properties.put("status", actual.getStatus());
		properties.put("ziel", actual.getReiseAngebot().getLand().getName());

		// mail Reisender
		String[] to = { actual.getReisender().getEmail() };

		try {
			// export booking pdf
			byte[] export = exportPdf(actual.getId());

			// data source to write the exported pdf into
			DataSource source = new FileDataSource(ResourceUtils.getFile(templateLink));

			OutputStream sourceOS = source.getOutputStream();
			sourceOS.write(export);
			if (buchung.getSendMail()) {
				sendMail(properties, to, "Aktualisierung der Reservierung", template_update_booking, source, actual);
			}
		} catch (Exception e) {
			log.error("Error during exporting pdf: {}", e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
		log.info("Status Successfully updated");
		return Buchung2BuchungReadTO.apply(actual);

		// send mail on updated status
		/*
		 * if (buchung.getStatus() != null && buchung.getStatus() != actual.getStatus())
		 * { actual.setStatus(buchung.getStatus()); // template params Map<String,
		 * Object> properties = new HashMap<>(); properties.put("name",
		 * actual.getReisender().getName()); properties.put("status",
		 * actual.getStatus()); properties.put("ziel",
		 * actual.getReiseAngebot().getLand().getName());
		 *
		 * String[] to = {actual.getReisender().getEmail()};
		 *
		 * // export booking pdf byte[] export = exportPdf(actual.getId());
		 *
		 * // data source to write the exported pdf into // when running local
		 * //DataSource source = new
		 * FileDataSource(ResourceUtils.getFile("classpath:Booking.jrxml")); // when
		 * running on the server DataSource source = new
		 * FileDataSource(ResourceUtils.getFile(templateLink));
		 *
		 * OutputStream sourceOS = source.getOutputStream(); sourceOS.write(export);
		 *
		 * sendMail(properties, to, "Aktualisierung der Reservierung",
		 * template_update_booking, source); }
		 */

		// changeStatus(buchung.getId(), buchung.getStatus().toString()); //todo for
		// change status
	}

	public ResponseEntity<?> removeMitReisender(UUID id) {
		Buchung buchung = findBuchung(id);

		buchung.setMitReisenderId(null);

		buchungRepository.save(buchung);
		log.info("successfully removed");

		Reisender mitReisender = reisenderService.findReisender(buchung.getMitReisenderId());
		reisenderService.deleteReisender(mitReisender.getId());

		// update freiPlaetze after deleting a new Buchung
		ReiseAngebot ra = buchung.getReiseAngebot();
		ra.setFreiPlaetze(buchung.getReiseAngebot().getFreiPlaetze() + 1);
		reiseAngebotRepository.save(ra);

		return new ResponseEntity<>("Mitreiser Successfully deleted", HttpStatus.OK);
	}

	public ResponseEntity<?> changeStatus(ChangeStatus changeStatus) {
		Buchung buchung = findBuchung(changeStatus.getId());

		// update status and send mail
		if (changeStatus.getStatus() != null && !buchung.getStatus().toString().equals(changeStatus.getStatus())) {

			// check the new status
			Buchungstatus toUpdate = null;
			try {
				toUpdate = Buchungstatus.valueOf(changeStatus.getStatus());
				buchung.setStatus(toUpdate);
			} catch (Exception e) {
				throw new ApiRequestException("Status is invalid");
			}

			// save
			buchung = buchungRepository.save(buchung);

			// template params
			Map<String, Object> properties = new HashMap<>();
			properties.put("name", buchung.getReisender().getName());
			properties.put("status", changeStatus.getStatus());
			properties.put("ziel", buchung.getReiseAngebot().getLand().getName());

			// mail Reisender
			String[] to = { buchung.getReisender().getEmail() };

			try {
				// export booking pdf
				byte[] export = exportPdf(buchung.getId());

				// data source to write the exported pdf into
				DataSource source = new FileDataSource(ResourceUtils.getFile(templateLink));

				OutputStream sourceOS = source.getOutputStream();
				sourceOS.write(export);
				if (changeStatus.isSendMail()) {
					if (buchung.getStatus().equals(Buchungstatus.Storniert)) {
						sendMail(properties, to, "Stornierung der Reservierung", template_delete_booking, source,
								buchung);
					} else {
						sendMail(properties, to, "Aktualisierung der Reservierung", template_update_booking, source,
								buchung);
					}
				}
			} catch (Exception e) {
				log.error("Error during exporting pdf: {}", e.getMessage());
				throw new ApiRequestException(e.getMessage());
			}
			log.info("Status Successfully updated");
			return ResponseEntity.ok(Buchung2BuchungReadTO.apply(buchung));
		}
		log.warn("Cannot update the Status");
		return new ResponseEntity<>("Cannot update the Status", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> deleteBuchung(UUID id) {
		Buchung buchung = findBuchung(id);

		buchungRepository.deleteById(buchung.getId());

		// update freiPlaetze after deleting a new Buchung
		ReiseAngebot ra = buchung.getReiseAngebot();
		ra.setFreiPlaetze(buchung.getReiseAngebot().getFreiPlaetze() + 1);
		reiseAngebotRepository.save(ra);

		log.info("successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	private void sendMail(Map<String, Object> properties, String[] to, String subject, String template,
			DataSource source, Buchung buchung) {

		Email email = new Email();

		email.setFrom(from);
		email.setReply(false);
		email.setTo(to);
		email.setSubject(subject);
		email.setTemplate(template);
		email.setProperties(properties);

		mailService.sendHtmlMessage(email);
		log.info("Successfully sent mail");
//      sending with attachment is not needed a now		
//		if (source != null) {
//			mailService.sendHtmlMessageAttachment(email, source, buchung);
//			log.info("Successfully sent mail");
//		} else {
//			mailService.sendHtmlMessage(email);
//			log.info("Successfully sent mail");
//		}

	}

	public byte[] exportPdf(UUID id) throws JRException, URISyntaxException, IOException {

		// since we generate pdfs with html we don't need the jasper approach for the
		// moment
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

		// jasper export not need for the moment
		// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
		// new JREmptyDataSource());

		// export pdf with jasper
		// byte[] export = JasperExportManager.exportReportToPdf(jasperPrint);

		Buchung buchung = findBuchung(id);

		Map<String, Object> params = getBookingMetadata(id);

		// export pdf with html
		byte[] export_html = generatePdfFile(params,
				buchung.getReiseAngebot().getLand().getName().substring(0, 3).toUpperCase(Locale.ROOT) + "_"
						+ buchung.getReiseAngebot().getStartDatum().getYear() % 100 + "_"
						+ buchung.getReisender().getName() + "_"
						+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".pdf");

		return export_html;
	}

	private Map<String, Object> getBookingMetadata(UUID id) {

		Buchung buchung = findBuchung(id);

		Buchungsklassen tarif = buchungsklassenService.findBuchungsklasse(buchung.getBuchungsklasseId());

		ReiseAngebot ra = reiseAngebotService.findReiseAngebot(buchung.getReiseAngebot().getId());

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
			Reisender mitReisender = reisenderRepository.findById(buchung.getMitReisenderId()).get();
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
			params.put("abFlughafenMitReisender", buchung.getAbFlughafenMitReisender());
			params.put("ruckFlughafenMitReisender", buchung.getRuckFlughafenMitReisender() != null ? buchung.getRuckFlughafenMitReisender() : "");
			params.put("handGepaeckMitReisender", buchung.getHandGepaeckMitReisender() == true ? "ja" : "nein");
			params.put("kofferMitReisender", buchung.getKofferMitReisender() != null ? buchung.getKofferMitReisender() : "" );
		}

		params.put("copyright_monat_jahr", LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear());
		params.put("buchung_datum", buchung.getBuchungDatum().toString());
		params.put("buchungsnummer", buchung.getBuchungsnummer());

		params.put("abFlughafenReisender", buchung.getAbFlughafenReisender());

		params.put("ruckFlughafenReisender", buchung.getRuckFlughafenReisender());

		params.put("handGepaeckReisender", buchung.getHandGepaeckReisender() == true ? "ja" : "nein");

		params.put("kofferReisender", buchung.getKofferReisender());

		params.put("buchungsklasse", tarif.getType());
		params.put("zahlungsmethode", buchung.getZahlungMethod().toString());

		params.put("jahr", "" + LocalDate.now().getYear());
		params.put("buchungsstatus", buchung.getStatus());

		return params;
	}

	// pdf filename: Ex: LandName_22_ReisenderName_2022-05-19.pdf
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
		} catch (FileNotFoundException | DocumentException e) {
			log.error(e.getMessage(), e);
		}

		// return as byte[]
		return Files.toByteArray(file);
	}

	public Buchung findBuchung(UUID id) {
		return buchungRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Booking with id" + id));
	}

	public String createBuchungsnummer(String land, String name, String vorname, int lastBuchungsnummer) {

		StringBuilder str = new StringBuilder();
		land = land.substring(0, 3).toUpperCase(Locale.ROOT);
		int saison = (LocalDate.now().getYear()) % 100;
		name = name.substring(0, 0);
		vorname = vorname.substring(0, 0);

		String nummer = "";

		// can be null
		/*
		 * Optional<Buchung> buchung = buchungRepository.findFirstByOrderByNummerDesc();
		 * int lastBuchungsnummer = 0; if (buchung.isPresent()) { lastBuchungsnummer =
		 * buchung.get().getNummer(); }
		 */

		// format nummer to XXX

		int newNummer = lastBuchungsnummer;
		if (newNummer < 10) {// X
			nummer = "00" + newNummer;

		} else if (newNummer < 100 && newNummer > 10) {// XX
			nummer = "0" + newNummer;
		} else if (newNummer > 99) {
			nummer = Integer.toString(newNummer);
		}

		str.append(land).append(saison).append(name).append(vorname).append(nummer);
		return str.toString();
	}
}
