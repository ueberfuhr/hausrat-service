package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface InsuranceCalculationRepository {

    Flux<InsuranceCalculationResult> findAll();

    Mono<InsuranceCalculationResult> findById(long id);

    @Validated
    Mono<InsuranceCalculationResult> save(@NotNull @Valid InsuranceCalculationResult result);
}
