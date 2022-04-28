package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
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
