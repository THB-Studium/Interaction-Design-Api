package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadReadTO {

    private UUID id;

    private String name;

    @Lob
    private String description;

    private byte[] bild;

    private UUID landId;
}
