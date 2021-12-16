package de.sample.hausrat.domain.repository;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.model.ProductName;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface ProductRepository extends InternalProductRepository {

    Mono<Long> getCount();

    Mono<Product> save(@NotNull @Valid Product product);

    Flux<Product> findAll();

    Mono<Product> find(@ProductName String name);

    Mono<Boolean> delete(@ProductName String name);

}
