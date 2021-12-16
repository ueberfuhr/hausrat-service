package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.Product;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

public interface InternalProductRepository {

    /**
     * If the database is empty, the given products should be created initially.
     * @param initialProducts the products
     * @return the flux that returns the saved products
     */
    Flux<Product> initialize(Stream<Product> initialProducts);

    /**
     * If the database is empty, the given products should be created initially.
     * @param initialProducts the products
     * @return the flux that returns the saved products
     */
    default Flux<Product> initialize(Product... initialProducts) {
        return this.initialize(Stream.of(initialProducts));
    }

}
