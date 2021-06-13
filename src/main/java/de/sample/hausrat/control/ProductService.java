package de.sample.hausrat.control;

import de.sample.hausrat.control.model.Product;
import de.sample.hausrat.control.model.mappers.ProductMapper;
import de.sample.hausrat.control.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    @PostConstruct
    void initializeProducts() {
        if (this.repo.count() < 1) {
            this.save(new Product("COMPACT", 650));
            this.save(new Product("OPTIMAL", 700));
        }
    }

    public void save(Product product) {
        this.repo.save(mapper.map(product));
    }

    public Stream<Product> findAll() {
        return repo.findAll(Sort.by("name"))
                .stream()
                .map(mapper::map);
    }

    public Optional<Product> find(String name) {
        return repo.findById(name)
                .map(mapper::map);
    }

}
