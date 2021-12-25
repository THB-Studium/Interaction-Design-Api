package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reiser {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotBlank
	private String name;

	@NotBlank
	private String vorname;

	private Date geburtsdatum;

	private long telefonnummer;

	@NotBlank
	private String email;

	private String hochschule;

	@NotBlank
	private String adresse;

	private String studiengang;

	private String arbeitBei;

	private boolean schonTeilgenommen;

	@OneToMany(mappedBy = "reiser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Buchung> buchungen;

}
