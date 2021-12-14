package de.sample.hausrat.persistence;

import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import de.sample.hausrat.domain.repository.InsuranceCalculationRepository;
import de.sample.hausrat.persistence.mappers.InsuranceCalculationEntityMapper;
import de.sample.hausrat.persistence.repository.InsuranceCalculationR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InsuranceCalculationRepositoryImpl implements InsuranceCalculationRepository {

    private final InsuranceCalculationR2dbcRepository repo;
    private final InsuranceCalculationEntityMapper mapper;

    @Override
    public Flux<InsuranceCalculationResult> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "timestamp", "id"))
          .map(mapper::map);
    }

    @Override
    public Mono<InsuranceCalculationResult> findById(long id) {
        return repo.findById(id)
          .map(mapper::map);
    }

    @Override
    public Mono<InsuranceCalculationResult> save(InsuranceCalculationResult result) {
        return repo.save(mapper.map(result))
          .map(mapper::map);
    }
}
