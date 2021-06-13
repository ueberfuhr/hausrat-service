package de.sample.hausrat.control.repository;

import de.sample.hausrat.entity.InsuranceCalculationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceCalculationRepository extends JpaRepository<InsuranceCalculationEntity, Long> {

    // we could add further queries here, e.g.
    //List<InsuranceCalculationEntity> findByProduct();

}
