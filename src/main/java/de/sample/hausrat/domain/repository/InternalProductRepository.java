package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.Product;

import java.util.stream.Stream;

public interface InternalProductRepository {

    /**
     * If the database is empty, the given products should be created initially.
     * @param initialProducts the products
     */
    void initialize(Stream<Product> initialProducts);

    /**
     * If the database is empty, the given products should be created initially.
     * @param initialProducts the products
     */
    default void initialize(Product... initialProducts) {
        this.initialize(Stream.of(initialProducts));
    }

}
