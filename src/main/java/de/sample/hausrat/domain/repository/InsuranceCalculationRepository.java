package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

@Validated
public interface InsuranceCalculationRepository {

    Stream<InsuranceCalculationResult> findAll();

    Optional<InsuranceCalculationResult> findById(long id);

    @Validated
    InsuranceCalculationResult save(@NotNull @Valid InsuranceCalculationResult result);
}
