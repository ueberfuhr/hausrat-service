package de.sample.hausrat.control;

import de.sample.hausrat.control.model.InsuranceCalculationRequest;
import de.sample.hausrat.control.model.InsuranceCalculationResult;
import de.sample.hausrat.control.model.Price;
import de.sample.hausrat.control.model.mappers.InsuranceCalculationMapper;
import de.sample.hausrat.control.repository.InsuranceCalculationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InsuranceCalculationService {

    private final InsuranceCalculator calculator;
    private final InsuranceCalculationRepository repo;
    private final InsuranceCalculationMapper mapper;

    public Stream<InsuranceCalculationResult> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "timestamp", "id"))
                .stream()
                .map(mapper::map);
    }

    public Optional<InsuranceCalculationResult> findById(long id) {
        return repo.findById(id)
                .map(mapper::map);
    }

    @Transactional
    public InsuranceCalculationResult process(@Valid InsuranceCalculationRequest request) {
        Price price = calculator.calculate(request);
        InsuranceCalculationResult result = new InsuranceCalculationResult();
        result.setRequest(request);
        result.setTimestamp(LocalDateTime.now());
        result.setValue(price.getValue());
        result.setCurrency(price.getCurrency());
        return mapper.map(repo.save(mapper.map(result)));
    }

}
