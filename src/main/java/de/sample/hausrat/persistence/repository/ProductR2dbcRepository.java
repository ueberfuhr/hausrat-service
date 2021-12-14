package de.sample.hausrat.persistence.repository;

import de.sample.hausrat.persistence.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductR2dbcRepository extends R2dbcRepository<ProductEntity, String> {
}
