package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Delegate
    private final ProductRepository repo;

    //@PostConstruct
    void initializeProducts() {
        if (this.repo.getCount().block() < 1) { // synchronous initialization
            this.save(new Product("COMPACT", 650));
            this.save(new Product("OPTIMAL", 700));
        }
    }

}
