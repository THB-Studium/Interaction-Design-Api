package com.team.angular.interactiondesignapi.transfertobjects.unterkunft;

import java.util.List;
import java.util.UUID;

import com.team.angular.interactiondesignapi.transfertobjects.land.LandReadTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnterkunftReadTO {

	private UUID id;

	private String name;

	private String link;

	private String adresse;

	private String beschreibung;

	private LandReadTO land;

	private List<byte[]> bilder;

}
