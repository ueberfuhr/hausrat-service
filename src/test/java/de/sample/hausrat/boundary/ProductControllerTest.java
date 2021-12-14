package de.sample.hausrat.boundary;

import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.boundary.model.mappers.ProductDtoMapper;
import de.sample.hausrat.domain.ProductService;
import de.sample.hausrat.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers=ProductController.class)
class ProductControllerTest {

    @Autowired
    ProductController controller;
    @MockBean
    ProductService service;
    @MockBean
    ProductDtoMapper mapper;

    @Test
    @DisplayName("when service returns empty list, controller will return empty list too")
    void testEmptyProducts() {
        // given
        when(service.findAll()).thenReturn(Flux.empty());
        // when~then
        StepVerifier.create(controller.getProducts())
          .expectComplete()
          .verify();
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("when service returns single element, controller will return single value too")
    void testSingleProduct() {
        // given
        var product = new Product();
        var productDto = new ProductDto();
        when(service.findAll()).thenReturn(Flux.just(product));
        when(mapper.map(product)).thenReturn(productDto);
        // when~then
        StepVerifier.create(controller.getProducts())
          .expectNext(productDto)
          .expectComplete()
          .verify();
    }

}
