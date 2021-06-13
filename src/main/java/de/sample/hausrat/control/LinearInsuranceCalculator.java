package de.sample.hausrat.control;

import de.sample.hausrat.control.model.InsuranceCalculationRequest;
import de.sample.hausrat.control.model.Price;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class LinearInsuranceCalculator implements InsuranceCalculator {
    @Override
    public Price calculate(@Valid InsuranceCalculationRequest request) {
        return null;
    }
}
