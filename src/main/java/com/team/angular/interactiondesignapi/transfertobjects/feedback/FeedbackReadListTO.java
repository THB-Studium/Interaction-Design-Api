package com.team.angular.interactiondesignapi.transfertobjects.feedback;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackReadListTO {
	
	private UUID id;
	
	private String autor;
	
	private boolean veroefentlich;
	
	private String description;

}
