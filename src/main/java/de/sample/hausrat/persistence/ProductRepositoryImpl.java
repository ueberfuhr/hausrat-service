package de.sample.hausrat.persistence;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.repository.ProductRepository;
import de.sample.hausrat.persistence.mappers.ProductEntityMapper;
import de.sample.hausrat.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository repo;
    private final ProductEntityMapper mapper;

    @Override
    public long getCount() {
        return this.repo.count();
    }

    @Override
    public Product save(Product product) {
        return this.mapper.map(this.repo.save(this.mapper.map(product)));
    }

    @Override
    public Stream<Product> findAll() {
        return this.repo.findAll(Sort.by("name")).stream().map(mapper::map);
    }

    @Override
    public Optional<Product> find(String name) {
        return this.repo.findById(name).map(mapper::map);
    }

    @Override
    public void delete(String name) {
        this.repo.deleteById(name);
    }
}
