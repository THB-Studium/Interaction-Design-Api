package com.team.angular.interactiondesignapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Unterkunft {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotBlank
	private String name;

	private String link;

	//@NotBlank
	private String addresse;

	private String beschreibung;

	@ElementCollection
	private List<byte[]> bilder;

	@ManyToOne(fetch = FetchType.LAZY)
	private Land land;

}
