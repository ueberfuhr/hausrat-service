package de.sample.hausrat.control.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * A request to the {@link de.sample.hausrat.control.InsuranceCalculator}.
 */
@Data
@AllArgsConstructor
public class InsuranceCalculationRequest {

    @NotNull
    private final String product;
    @Positive
    private final double livingArea;

}
