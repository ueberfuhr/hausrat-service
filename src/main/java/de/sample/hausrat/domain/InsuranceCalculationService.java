package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import de.sample.hausrat.domain.model.Price;
import de.sample.hausrat.domain.repository.InsuranceCalculationRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceCalculationService {

    private final InsuranceCalculator calculator;
    @Delegate
    private final InsuranceCalculationRepository repo;
    private final Optional<Authentication> authentication;

    @Transactional
    public InsuranceCalculationResult process(@Valid InsuranceCalculationRequest request) {
        Price price = calculator.calculate(request);
        InsuranceCalculationResult result = new InsuranceCalculationResult();
        result.setRequest(request);
        result.setTimestamp(LocalDateTime.now());
        result.setValue(price.getValue());
        result.setCurrency(price.getCurrency());
        result.setPrincipal(authentication.map(Authentication::getPrincipal ).map(Object::toString).orElse(null));
        return this.save(result);
    }

}
