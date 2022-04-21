package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen.BuchungsklassenReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.erwartungen.ErwartungenReadListTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReiseAngebotReadTO {
    private UUID id;

    private String titel;

    private byte[] startbild;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDatum;

    private int plaetze;

    private int freiPlaetze;

    private int interessiert;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anmeldungsFrist;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    private List<BuchungsklassenReadListTO> buchungsklassenReadListTO;

    private ErwartungenReadListTO erwartungenReadListTO;

    private UUID landId;

    private String hinweise;

    private List<String> mitreiseberechtigt;

    private String sonstigeHinweise;

}
