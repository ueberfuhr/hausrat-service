package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.model.ProductName;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.stream.Stream;

@Validated
public interface ProductRepository extends InternalProductRepository {

    long getCount();

    Product save(@NotNull @Valid Product product);

    Stream<Product> findAll();

    Optional<Product> find(@ProductName String name);

    boolean delete(@ProductName String name);

}
