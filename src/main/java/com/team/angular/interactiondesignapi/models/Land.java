package com.team.angular.interactiondesignapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

	private String hinweise;

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<String> mitReiserBerechtigt;

	private String sonstigeHinweise;

    @OneToOne(fetch = FetchType.LAZY)
    private ReiseAngebot reiseAngebot;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private List<LandInfo> landInfo;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Highlight> highlights;

	@OneToMany(mappedBy = "land", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Unterkunft> unterkunft;
}
