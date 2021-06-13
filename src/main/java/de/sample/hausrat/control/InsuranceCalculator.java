package de.sample.hausrat.control;

import de.sample.hausrat.control.model.InsuranceCalculationRequest;
import de.sample.hausrat.control.model.Price;

/**
 * Provides the calculation of the sum insured based on
 * the {@link de.sample.hausrat.control.model.Product} and the living space.
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
    Price calculate(InsuranceCalculationRequest request);

}
