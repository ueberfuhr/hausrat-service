package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.Price;

import javax.validation.Valid;

/**
 * Provides the calculation of the sum insured based on
 * the {@link de.sample.hausrat.domain.model.Product} and the living space.
 */
@FunctionalInterface
public interface InsuranceCalculator {

    /**
     * Calculates the sum insured.
     *
     * @param request the request
     * @return the sum insured
     * @throws IllegalArgumentException, if the request is invalid or <tt>null</tt>
     */
    Price calculate(@Valid InsuranceCalculationRequest request);

}
