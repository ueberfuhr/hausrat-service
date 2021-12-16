package de.sample.hausrat.domain;

import de.sample.hausrat.config.exceptions.Throw;
import de.sample.hausrat.domain.exceptions.ThrowServiceException;
import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import de.sample.hausrat.domain.model.Price;
import de.sample.hausrat.domain.repository.InsuranceCalculationRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Throw
@ThrowServiceException
public class InsuranceCalculationService {

    private final InsuranceCalculator calculator;
    @Delegate
    private final InsuranceCalculationRepository repo;
    private final ObjectProvider<Authentication> authentication;

    @Transactional
    public InsuranceCalculationResult process(
      @Valid InsuranceCalculationRequest request) {
        Price price = calculator.calculate(request);
        InsuranceCalculationResult result = new InsuranceCalculationResult();
        result.setRequest(request);
        result.setTimestamp(LocalDateTime.now());
        result.setValue(price.getValue());
        result.setCurrency(price.getCurrency());
        authentication.stream().findFirst()
          .map(Authentication::getPrincipal)
          .map(Object::toString)
          .ifPresent(result::setPrincipal);
        return this.save(result);
    }

}
