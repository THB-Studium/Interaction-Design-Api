package com.team.angular.interactiondesignapi.transfertobjects.buchungsklassen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Buchungsklassen Model")
public class BuchungsklassenReadWriteTO {

    @ApiModelProperty(notes = "ID of the Buchungsklasse", name = "id", required = false)
    private UUID id;

    @ApiModelProperty(notes = "Name of the Buchungsklasse", name = "type", required = true, value = "**-TARIF")
    private String type;

    @ApiModelProperty(notes = "Description of the Buchungsklasse", name = "description", required = true, value = "mit privatem Bad")
    private String description;

    @ApiModelProperty(notes = "Preis of the Buchungsklasse", name = "preis", required = true, value = "1000")
    private double preis;

    private UUID reiseAngebotId;

}
