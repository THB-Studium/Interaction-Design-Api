package com.team.angular.interactiondesignapi.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Buchung {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	private Date datum;

	private String mitReiser;
	
	@OneToOne(mappedBy = "buchung")
	private Buchungsklassen tarif;

	@NotBlank
	private String flugAhfen;

	private String handGepaeck;

	private String koffer;

	@Enumerated(EnumType.STRING)
	private ZahlungMethod zahlungMethod;

	@ManyToOne
	@JoinColumn(name = "Reiser_id")
	private Reiser reiser;

	@ManyToOne
	@JoinColumn(name = "Land_id")
	private Land land;

}
