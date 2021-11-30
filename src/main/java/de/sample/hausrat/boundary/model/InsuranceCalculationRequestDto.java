package de.sample.hausrat.boundary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.sample.hausrat.domain.model.ProductName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@ApiModel(value = "InsuranceCalculationRequest", description = "The request for an insurance calculation.")
@Data
public class InsuranceCalculationRequestDto {

    @ApiModelProperty("The name of the product.")
    @ProductName
    private String product;

    @ApiModelProperty("The id to identify the calculation.")
    @Positive
    @JsonProperty("living_area")
    private double livingArea;

}
