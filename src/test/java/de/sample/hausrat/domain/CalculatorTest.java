package de.sample.hausrat.domain;

import de.sample.hausrat.domain.model.InsuranceCalculationRequest;
import de.sample.hausrat.domain.model.Price;
import de.sample.hausrat.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

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
            case "COMPACT": return Optional.of(new Product("COMPACT", 650));
            case "OPTIMAL": return Optional.of(new Product("OPTIMAL", 700));
            default: return Optional.empty();
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
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideCalculations() {
        return Stream.of( //
          Arguments.of("COMPACT", 100, BigDecimal.valueOf(6500000, 2), "EUR"), //
          Arguments.of("COMPACT", 1, BigDecimal.valueOf(65000, 2), "EUR"), //
          Arguments.of("OPTIMAL", 100, BigDecimal.valueOf(7000000, 2), "EUR"), //
          Arguments.of("OPTIMAL", 1, BigDecimal.valueOf(70000, 2), "EUR") //
        );
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
        return Stream.of( //
          Arguments.of("COMPACT", -1), //
          Arguments.of("COMPACT", 0), //
          Arguments.of("NOTEXISTING", 100) //
        );
    }

}
