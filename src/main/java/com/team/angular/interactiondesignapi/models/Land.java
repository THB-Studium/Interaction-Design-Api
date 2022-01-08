package com.team.angular.interactiondesignapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;
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

	private byte[] karte_bild;

    @OneToOne(mappedBy = "land",fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LandInfo> landInfo;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Highlight> highlights;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Unterkunft> unterkunft;

}
