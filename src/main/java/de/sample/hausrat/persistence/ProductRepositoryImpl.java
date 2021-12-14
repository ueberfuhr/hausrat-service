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
    public Mono<Product> save(Product product) {
        return this.repo.save(this.mapper.map(product))
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
    public Mono<Void> delete(String name) {
        return this.repo.deleteById(name);
    }
}
