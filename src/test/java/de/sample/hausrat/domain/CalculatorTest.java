package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.Price;
import de.sample.hausrat.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static pl.rzrz.assertj.reactor.Assertions.assertThat;

@SpringBootTest
class CalculatorTest {

    @Autowired
    InsuranceCalculator calculator;
    @MockBean
    ProductService productService;

    @BeforeEach
    void setup() {
        lenient().when(productService.find(anyString())).thenAnswer(i -> {
            switch (i.getArgument(0).toString()) {
            case "COMPACT":
                return Mono.just(new Product("COMPACT", 650));
            case "OPTIMAL":
                return Mono.just(new Product("OPTIMAL", 700));
            case "invalid-name":
                return Mono.just(new Product("invalid-name", 1));
            default:
                return Mono.empty();
            }
        });
    }

    @ParameterizedTest
    @MethodSource("provideCalculations")
    void testCalculations(String product, double livingArea, BigDecimal expectedValue, String expectedCurrency) {
        // given
        var req = new InsuranceCalculationRequest(product, livingArea);
        var expected = new Price(expectedValue, expectedCurrency);
        // when
        var result = calculator.calculate(req);
        // then
        assertThat(result).emits(expected);
    }

    private static Stream<Arguments> provideCalculations() {
        return Stream.of( //
          Arguments.of("COMPACT", 100, BigDecimal.valueOf(6500000, 2), "EUR"),
          Arguments.of("COMPACT", 1, BigDecimal.valueOf(65000, 2), "EUR"),
          Arguments.of("OPTIMAL", 100, BigDecimal.valueOf(7000000, 2), "EUR"),
          Arguments.of("OPTIMAL", 1, BigDecimal.valueOf(70000, 2), "EUR")
        );
    }

    @DisplayName("Deferred validation when using invalid product name")
    @Test
    void testInvalidProduct() {
        // given
        var req = new InsuranceCalculationRequest("NOTEXISTING", 100);
        // when
        var result = calculator.calculate(req);
        // then
        assertThat(result).sendsError(v -> assertThat(v).isInstanceOf(ValidationException.class));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCalculations")
    void testInvalidCalculations(String product, double livingArea) {
        // given
        var req = new InsuranceCalculationRequest(product, livingArea);
        // when~then
        assertThatThrownBy(() -> calculator.calculate(req))  //
          .isInstanceOf(ValidationException.class);
    }

    private static Stream<Arguments> provideInvalidCalculations() {
        return Stream.of(
          Arguments.of("COMPACT", -1),
          Arguments.of("COMPACT", 0),
          Arguments.of("invalid-name", 100)
        );
    }

}
