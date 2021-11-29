package de.sample.hausrat.persistence.repository;

import de.sample.hausrat.entity.InsuranceCalculationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCalculationJpaRepository extends JpaRepository<InsuranceCalculationEntity, Long> {

}
