package de.sample.hausrat.domain;

import de.sample.hausrat.config.properties.CalculationCurrencyProperties;
import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.Price;
import de.sample.hausrat.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
@Validated
public class LinearInsuranceCalculator implements InsuranceCalculator {

    private final ProductService service;
    private final CalculationCurrencyProperties currencyProperties;

    private Price price(BigDecimal value) {
        return new Price(
          value.setScale(currencyProperties.getPrecision(), RoundingMode.HALF_UP),
          currencyProperties.getCode()
        );
    }

    private Price price(double value) {
        return price(BigDecimal.valueOf(value));
    }

    @Override
    public Price calculate(InsuranceCalculationRequest req) {
        Product product = service.find(req.getProduct())
          .orElseThrow(() -> new ValidationException(
            String.format("No product could be found with name \"%s\"!", req.getProduct())
          ));
        return price(req.getLivingArea() * product.getPrice());
    }
}
