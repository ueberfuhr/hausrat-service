package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
@Validated
public class LinearInsuranceCalculator implements InsuranceCalculator {

    private final ProductService service;

    // see application.yml
    @Value("${calculation.currency.precision:2}")
    private int currencyPrecision;
    @Value("${calculation.currency.code:EUR}")
    private String currencyCode;

    private Price price(BigDecimal value) {
        return new Price(value.setScale(currencyPrecision, RoundingMode.HALF_UP), currencyCode);
    }

    private Price price(double value) {
        return price(BigDecimal.valueOf(value));
    }

    @Override
    public Mono<Price> calculate(final InsuranceCalculationRequest req) {
        return service.find(req.getProduct())
          .map(product -> price(req.getLivingArea() * product.getPrice()))
          .or(Mono.error(() -> new ValidationException(
            String.format("No product could be found with name \"%s\"!", req.getProduct())
          )));
    }
}
