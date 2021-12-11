package com.team.angular.interactiondesignapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Land {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotBlank
	private String name;

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> flughafen;

	private String unterkunft_text;

	private String corona_infos;

	private byte[] karte_bild;

	private String klima;

	private String gesundheit;

	private String reiseOrdnung;

	private String hinweise;

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> mitReiserBerechtigt;

	private String sonstigeHinweise;

    @OneToOne(fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY)
	private List<Land_info> land_info;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY)
	private List<Highlight> highlights;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY)
	private List<Unterkunft> unterkunft;

}
