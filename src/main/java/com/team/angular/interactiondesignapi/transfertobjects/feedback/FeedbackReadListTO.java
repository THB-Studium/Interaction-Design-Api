package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackReadListTO {

    private UUID id;

    private String autor;

    private boolean veroeffentlich;

    private String description;

}
