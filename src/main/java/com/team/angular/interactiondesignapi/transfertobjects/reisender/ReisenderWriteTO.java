package com.team.angular.interactiondesignapi.transfertobjects.reisender;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReisenderWriteTO {

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

}
