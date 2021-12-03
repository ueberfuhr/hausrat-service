package de.sample.hausrat.boundary.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "InsuranceCalculationResult", description = "The result of an insurance calculation.")
@Data
public class InsuranceCalculationResultDto {

    @ApiModelProperty("The id to identify the calculation.")
    @NotNull
    private Long id;

    @ApiModelProperty("The request that triggered the calculation.")
    @NotNull
    @Valid
    private InsuranceCalculationRequestDto request;

    @ApiModelProperty("The account that triggered the calculation.")
    private String principal;

    @ApiModelProperty("The value of the sum insured.")
    @NotNull
    private BigDecimal value;

    @ApiModelProperty("The currency of the sum insured.")
    @NotNull
    private String currency;

    @ApiModelProperty("The date when the calculation was done.")
    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;

}
