package de.sample.hausrat.domain;

import de.sample.hausrat.config.exceptions.Throw;
import de.sample.hausrat.domain.exceptions.ThrowServiceException;
import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.repository.InternalProductRepository;
import de.sample.hausrat.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Throw
@ThrowServiceException
@Slf4j
public class ProductService {

    @Delegate(excludes = InternalProductRepository.class)
    private final ProductRepository repo;

    @PostConstruct
    void initializeProducts() {
        this.repo.initialize(
          new Product("COMPACT", 650),
          new Product("OPTIMAL", 700)
        ).subscribe(p -> ProductService.log.info("Initialized Product Database with {}", p));
    }

}
