package com.team.angular.interactiondesignapi.transfertobjects.land;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadListTO;
import com.team.angular.interactiondesignapi.transfertobjects.hightlights.HighlightReadTO;
import com.team.angular.interactiondesignapi.transfertobjects.unterkunft.UnterkunftReadListTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandReadTO {

	private UUID id;

	private String name;

	private byte[] bild;

	private Date startDatum;

	private Date endDatum;

	private String titel;

	private byte[] karteBild;

	private int plaetze;

	private int freiPlaetze;

	private Date anmeldungsFrist;

	private Set<String> leistungen;

	private UUID erwartungenId;

	private UUID InfosLandId;

	private UUID buchungsklassenId;

	private List<HighlightReadTO> highlights;

	private Set<BuchungReadListTO> buchungen;

	private List<UnterkunftReadListTO> unterkunft;

}
