package com.team.angular.interactiondesignapi.transfertobjects.reiser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.angular.interactiondesignapi.transfertobjects.buchung.BuchungReadTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReiserReadTO {

    private UUID id;

    private String name;

    private String vorname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate geburtsdatum;

    private String telefonnummer;

    private String email;

    private String hochschule;

    private String adresse;

    private String studiengang;

    private String status;

    private String arbeitBei;

    private boolean schonTeilgenommen;

    private Set<BuchungReadTO> buchungen;

}
