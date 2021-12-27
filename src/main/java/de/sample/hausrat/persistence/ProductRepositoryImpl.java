package de.sample.hausrat.persistence;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.domain.repository.ProductRepository;
import de.sample.hausrat.persistence.config.DatabasePopulatedEvent;
import de.sample.hausrat.persistence.mappers.ProductEntityMapper;
import de.sample.hausrat.persistence.repository.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.time.Duration;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class ProductRepositoryImpl implements ProductRepository, ApplicationListener<DatabasePopulatedEvent> {

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
        return this.repo.existsById(name)
          .filter(result -> result)
          .flatMap(value -> this.repo.deleteById(name).then(Mono.just(true)))
          .switchIfEmpty(Mono.just(false));
    }

    @Override
    public Flux<Product> initialize(Stream<Product> initialProducts) {
        return this.repo.count()
          .filter(value -> value < 1)
          .flatMapMany(value ->
            Flux.fromStream(initialProducts)
          ).flatMap(this::save);
    }

    @Override
    public void onApplicationEvent(DatabasePopulatedEvent event) {
        Mono<Object> objectMono = Mono.create(sink -> {
            sink.success();
        });
    }

    public static void main(String[] args) throws InterruptedException {
        Mono.create(sink -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sink.success("test1");
        }).log(Loggers.getLogger("test1")).subscribeOn(Schedulers.boundedElastic()).subscribe(System.out::println);
        Mono.just("test2").log(Loggers.getLogger("test2")).subscribe(System.out::println);
        Mono.defer(() -> Mono.just("test3")).log(Loggers.getLogger("test3")).subscribe(System.out::println);

        // Start a cold Publisher which emits 0,1,2 every sec.
        ConnectableFlux<Long> flux =  Flux.interval(Duration.ofSeconds(1)).publish();
        flux.connect();
        // Let's subscribe to that with multiple subscribers.
        flux.subscribe(i -> System.out.println("first_subscriber received value:" + i));
        Thread.sleep(3_000);
        // Let a second subscriber come after some time 3 secs here.
        flux.subscribe(i -> System.out.println("second_subscriber received value:" + i));
        Thread.sleep(10_000);
    }
}
