package de.sample.hausrat.boundary.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@ApiModel(value = "InsuranceCalculationRequest", description = "The request for an insurance calculation.")
@Data
public class InsuranceCalculationRequestDto {

    @ApiModelProperty("The name of the product.")
    @NotNull
    @Size(min = 1)
    private String product;

    @ApiModelProperty("The id to identify the calculation.")
    @Positive
    @JsonProperty("living-area")
    private double livingArea;

}
