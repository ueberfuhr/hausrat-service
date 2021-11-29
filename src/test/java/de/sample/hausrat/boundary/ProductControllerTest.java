package de.sample.hausrat.boundary;

import de.sample.hausrat.InsuranceApplication;
import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.boundary.model.mappers.ProductDtoMapper;
import de.sample.hausrat.control.ProductService;
import de.sample.hausrat.control.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = InsuranceApplication.class)
@AutoConfigureTestDatabase
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
        when(service.findAll()).thenReturn(Stream.empty());
        // when
        Collection<ProductDto> result = controller.getProducts();
        // then
        assertAll( //
          () -> assertThat(result).isEmpty(), //
          () -> verifyNoInteractions(mapper) //
        );
    }

    @Test
    @DisplayName("when service returns single element, controller will return single value too")
    void testSingleProduct() {
        // given
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        when(service.findAll()).thenReturn(Stream.of(product));
        when(mapper.map(product)).thenReturn(productDto);
        // when
        Collection<ProductDto> result = controller.getProducts();
        // then
        assertThat(result).hasSize(1).contains(productDto);
    }

}
