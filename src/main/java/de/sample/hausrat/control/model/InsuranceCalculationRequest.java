package de.sample.hausrat.control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * A request to the {@link de.sample.hausrat.control.InsuranceCalculator}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCalculationRequest {

    @NotNull
    private String product;
    @Positive
    private double livingArea;

}
