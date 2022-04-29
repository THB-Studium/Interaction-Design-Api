package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import lombok.*;

import javax.persistence.Lob;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadWriteTO {

    private UUID id;

    private String name;

    @Lob
    private String description;

    private String bild;

    private UUID landId;
}
