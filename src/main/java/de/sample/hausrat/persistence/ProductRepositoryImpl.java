package de.sample.hausrat.persistence;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.repository.ProductRepository;
import de.sample.hausrat.persistence.mappers.ProductEntityMapper;
import de.sample.hausrat.persistence.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductR2dbcRepository repo;
    private final ProductEntityMapper mapper;

    @Override
    public Mono<Long> getCount() {
        return this.repo.count();
    }

    @Override
    public Mono<Product> save(final Product product) {
        // R2DBC is not able to detect whether the product still exists or not
        return this.repo.findById(product.getName())
          // if already existing, map the given product
          .map(p -> this.mapper.map(product))
          // else use R2DBC-specific implementation
          .switchIfEmpty(Mono.just(this.mapper.map(product).setAsNew()))
          .flatMap(this.repo::save)
          .map(this.mapper::map);
    }

    @Override
    public Flux<Product> findAll() {
        return this.repo.findAll(Sort.by("name"))
          .map(mapper::map);
    }

    @Override
    public Mono<Product> find(String name) {
        return this.repo.findById(name).map(mapper::map);
    }

    @Override
    public Mono<Boolean> delete(String name) {
        return this.repo.deleteById(name);
// TODO
        if (this.repo.existsById(name)) {
            this.repo.deleteById(name);
            return true;
        } else {
            return false;
        }

    }

    @Override // TODO
    public void initialize(Stream<Product> initialProducts) {
        if (this.repo.count() < 1) {
            initialProducts.forEach(this::save);
        }
    }

}
