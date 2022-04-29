package com.team.angular.interactiondesignapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReiseAngebot {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotBlank
    private String titel;

    private byte[] startbild;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDatum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDatum;

    @NotNull
    @Positive
    private int plaetze;

    private int freiPlaetze;

    private int interessiert;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate anmeldungsFrist;

    @Lob
    private String hinweise;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> mitreiseberechtigt;

    @Lob
    private String sonstigeHinweise;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> leistungen;

    @OneToMany(mappedBy = "reiseAngebot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Buchungsklassen> buchungsklassen;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Erwartungen erwartungen;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Land land;

    @OneToMany(mappedBy = "reiseAngebot", fetch = FetchType.LAZY)
    private List<Buchung> buchungen;

}
