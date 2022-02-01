package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnterkunftReadTO {

    private UUID id;

    private String name;

    private String link;

    private String adresse;

    @Lob
    private String beschreibung;

    private UUID landId;

    private List<byte[]> bilder;

}
