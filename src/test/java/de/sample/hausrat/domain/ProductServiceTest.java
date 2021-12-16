package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureTestDatabase
class ProductServiceTest {

    @Autowired
    ProductService service;

    @DisplayName("Saving a product increases the count and makes the product findable.")
    @Test
    void testSaveProduct() {
        var product = new Product("XYZ", 120);
        var countBefore = service.getCount();
        service.save(product);
        var countAfter = service.getCount();
        assertThat(countAfter).isEqualTo(countBefore + 1);
        assertThat(service.find(product.getName())).contains(product);
    }

    @DisplayName("Updating a product does not increase the count and makes the product findable.")
    @Test
    void testUpdateProduct() {
        var product = new Product("XYZ", 120);
        service.save(product); // setup -> the product must already exist
        var countBefore = service.getCount();
        product.setPrice(130); // update has to change value
        service.save(product);
        var countAfter = service.getCount();
        assertThat(countAfter).isEqualTo(countBefore);
        assertThat(service.find(product.getName())).contains(product);
    }

    @DisplayName("Deleting a product decreases the count and makes the product unfindable.")
    @Test
    void testDeleteProduct() {
        var product = new Product("XYZ", 120);
        service.save(product); // setup -> the product must already exist
        var countBefore = service.getCount();
        var result = service.delete(product.getName());
        var countAfter = service.getCount();
        assertAll(
          () -> assertThat(result).isTrue(),
          () -> assertThat(countAfter).isEqualTo(countBefore - 1)
        );
        assertThat(service.find(product.getName())).isEmpty();
    }

    @DisplayName("Deleting a product that does not exist will result in a negative result")
    @Test
    void testDeleteNonExistingProduct() {
        var productName = "XYZ";
        service.delete(productName); // setup -> the product must not exist
        var countBefore = service.getCount();
        var result = service.delete(productName);
        var countAfter = service.getCount();
        assertAll(
          () -> assertThat(result).isFalse(),
          () -> assertThat(countAfter).isEqualTo(countBefore)
        );
        assertThat(service.find(productName)).isEmpty();
    }

    @DisplayName("When updating an invalid product, a ValidationException is thrown. ")
    @Test
    void testUpdateInvalidProduct() {
        var product = new Product("", -120);
        var countBefore = service.getCount();
        assertThatThrownBy(() -> service.save(product))
          .isInstanceOf(ValidationException.class);
        var countAfter = service.getCount();
        assertThat(countAfter).isEqualTo(countBefore);
    }

}
