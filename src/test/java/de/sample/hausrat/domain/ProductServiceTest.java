package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static pl.rzrz.assertj.reactor.Assertions.assertThat;
import static pl.rzrz.assertj.reactor.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService service;

    @DisplayName("Saving a product increases the count and makes the product findable.")
    @Test
    void testSaveProduct() {
        var product = new Product("XYZ", 120);
        var countBefore = service.getCount().block();
        assertThat(countBefore).isNotNull();
        service.save(product).block();
        var countAfter = service.getCount().block();
        assertThat(countAfter).isEqualTo(countBefore + 1);
        assertThat(service.find(product.getName()).block()).isEqualTo(product);
    }

    @DisplayName("Updating a product does not increase the count and makes the product findable.")
    @Test
    void testUpdateProduct() {
        var product = new Product("XYZ", 120);
        service.save(product).block(); // setup -> the product must already exist
        var countBefore = service.getCount().block();
        assertThat(countBefore).isNotNull();
        product.setPrice(130); // update has to change value
        service.save(product).block();
        var countAfter = service.getCount().block();
        assertThat(countAfter).isEqualTo(countBefore);
        assertThat(service.find(product.getName())).emits(product);
    }

    @DisplayName("Deleting a product decreases the count and makes the product unfindable.")
    @Test
    void testDeleteProduct() {
        var product = new Product("XYZ", 120);
        service.save(product).block(); // setup -> the product must already exist
        var countBefore = service.getCount().block();
        assertThat(countBefore).isNotNull();
        var result = service.delete(product.getName()).block();
        var countAfter = service.getCount().block();
        assertAll(
          () -> assertThat(result).isTrue(),
          () -> assertThat(countAfter).isEqualTo(countBefore - 1)
        );
        assertThat(service.find(product.getName())).emitsCount(0);
    }

    @DisplayName("Deleting a product that does not exist will result in a negative result")
    @Test
    void testDeleteNonExistingProduct() {
        var productName = "XYZ";
        service.delete(productName).block(); // setup -> the product must not exist
        var countBefore = service.getCount().block();
        assertThat(countBefore).isNotNull();
        var result = service.delete(productName).block();
        var countAfter = service.getCount().block();
        assertAll(
          () -> assertThat(result).isFalse(),
          () -> assertThat(countAfter).isEqualTo(countBefore)
        );
        assertThat(service.find(productName)).emitsCount(0);
    }

    @DisplayName("When updating an invalid product, a ValidationException is thrown. ")
    @Test
    void testUpdateInvalidProduct() {
        var product = new Product("", -120);
        var countBefore = service.getCount().block();
        assertThatThrownBy(() -> service.save(product))
          .isInstanceOf(ValidationException.class);
        var countAfter = service.getCount().block();
        assertThat(countAfter).isEqualTo(countBefore);
    }

}
