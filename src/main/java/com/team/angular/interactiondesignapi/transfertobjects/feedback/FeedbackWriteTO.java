package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackWriteTO {

    private UUID id;

    private String autor;

    private boolean veroefentlich;

    private String description;

    private String bild;

}
