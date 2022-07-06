package com.team.angular.interactiondesignapi.transfertobjects.buchung;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStatus {
	
	UUID id;

	@NotBlank
	String status;

	boolean sendMail;

}
