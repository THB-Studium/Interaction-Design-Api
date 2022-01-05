package com.team.angular.interactiondesignapi.transfertobjects.reiseAngebot;

import com.team.angular.interactiondesignapi.models.Buchungsklassen;
import com.team.angular.interactiondesignapi.models.Erwartungen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReiseAngebotWriteTO {
    private UUID id;

    private String titel;

    private byte[] startbild;

    private Date startDatum;

    private Date endDatum;

    private int plaetze;

    private int freiPlaetze;

    private int interessiert;

    private Date anmeldungsFrist;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    private List<Buchungsklassen> buchungsklassen;

    private Erwartungen erwartungen;

    private UUID landId;
    
	private String hinweise;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> mitReiserBerechtigt;

	private String sonstigeHinweise;
}
