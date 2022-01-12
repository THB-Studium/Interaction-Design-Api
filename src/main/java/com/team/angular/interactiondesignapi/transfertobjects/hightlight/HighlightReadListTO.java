package com.team.angular.interactiondesignapi.transfertobjects.hightlight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighlightReadListTO {
    private UUID id;
    private String name;
    private String description;
}
