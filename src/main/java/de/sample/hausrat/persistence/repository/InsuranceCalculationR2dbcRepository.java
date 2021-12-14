package de.sample.hausrat.persistence.repository;

import de.sample.hausrat.persistence.entity.InsuranceCalculationEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCalculationR2dbcRepository extends R2dbcRepository<InsuranceCalculationEntity, Long> {

}
