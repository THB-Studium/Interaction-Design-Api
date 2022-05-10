package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
public class ReiseAngebotWriteTO {
    private UUID id;

    private String titel;

    private String startbild;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDatum;

    private int plaetze;

   // private int freiPlaetze;//??

    private int interessiert;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anmeldungsFrist;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    // private List<Buchungsklassen> buchungsklassen;

    // private Erwartungen erwartungen;

    private UUID landId;

    private String hinweise;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> mitreiseberechtigt;

    private String sonstigeHinweise;
}
