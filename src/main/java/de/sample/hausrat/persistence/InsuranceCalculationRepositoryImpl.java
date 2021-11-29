package de.sample.hausrat.persistence;

import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import de.sample.hausrat.domain.repository.InsuranceCalculationRepository;
import de.sample.hausrat.persistence.mappers.InsuranceCalculationEntityMapper;
import de.sample.hausrat.persistence.repository.InsuranceCalculationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InsuranceCalculationRepositoryImpl implements InsuranceCalculationRepository {

    private final InsuranceCalculationJpaRepository repo;
    private final InsuranceCalculationEntityMapper mapper;

    @Override
    public Stream<InsuranceCalculationResult> findAll() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "timestamp", "id")).stream().map(mapper::map);
    }

    @Override
    public Optional<InsuranceCalculationResult> findById(long id) {
        return repo.findById(id).map(mapper::map);
    }

    @Override
    public InsuranceCalculationResult save(InsuranceCalculationResult result) {
        return mapper.map(repo.save(mapper.map(result)));
    }
}
