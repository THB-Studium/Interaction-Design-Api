package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.UUID;

@Data
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
