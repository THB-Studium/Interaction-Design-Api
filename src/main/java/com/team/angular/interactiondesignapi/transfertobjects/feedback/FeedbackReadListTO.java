package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackReadListTO {

    private UUID id;

    private String autor;

    private boolean veroeffentlich;

    private String description;

}
