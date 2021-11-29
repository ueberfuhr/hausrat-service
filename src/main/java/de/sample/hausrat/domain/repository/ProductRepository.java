package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.Product;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.stream.Stream;

@Validated
public interface ProductRepository {

    long getCount();

    Product save(@NotNull @Valid Product product);

    Stream<Product> findAll();

    Optional<Product> find(@NotNull @Size(min = 1) String name);

}
