package de.sample.hausrat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

/**
 * A request to the {@link de.sample.hausrat.domain.InsuranceCalculator}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCalculationRequest {

    @ProductName
    private String product;
    @Positive
    private double livingArea;

}
