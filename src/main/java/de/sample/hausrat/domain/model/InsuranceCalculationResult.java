package de.sample.hausrat.domain.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InsuranceCalculationResult {

    private Long id;

    @NotNull
    @Valid
    private InsuranceCalculationRequest request;

    @NotNull
    private BigDecimal value;

    @NotNull
    private String currency;

    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;

}
