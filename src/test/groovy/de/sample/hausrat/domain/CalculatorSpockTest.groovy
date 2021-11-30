package de.sample.hausrat.domain

import de.sample.hausrat.domain.model.InsuranceCalculationRequest
import de.sample.hausrat.domain.model.Price
import de.sample.hausrat.domain.model.Product
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.validation.ValidationException

@SpringBootTest
class CalculatorSpockTest extends Specification {

    @Autowired
    InsuranceCalculator calculator
    @SpringBean
    ProductService productService = Mock() {
        find(_ as String) >> { String name ->
            switch (name) {
                case "COMPACT": return Optional.of(new Product("COMPACT", 650))
                case "OPTIMAL": return Optional.of(new Product("OPTIMAL", 700))
                case "invalid-name": return Optional.of(new Product("invalid-name", 1))
                default: return Optional.empty()
            }
        }
    }

    def "calculate for product #product with a living area of #livingArea"() {
        given:
        def req = new InsuranceCalculationRequest(product, livingArea)
        def expected = new Price(expectedValue, expectedCurrency)

        when:
        def result = calculator.calculate(req)

        then:
        result == expected

        where:
        product   | livingArea | expectedValue | expectedCurrency
        "COMPACT" | 100        | 65000.00G     | "EUR"
        "COMPACT" | 1          | 650.00G       | "EUR"
        "OPTIMAL" | 100        | 70000.00G     | "EUR"
        "OPTIMAL" | 1          | 700.00G       | "EUR"
    }

    def "throw validation error for product #product with a living area of #livingArea"() {
        given:
        def req = new InsuranceCalculationRequest(product, livingArea)

        when:
        calculator.calculate(req)

        then:
        thrown ValidationException

        where:
        product        | livingArea
        "COMPACT"      | -1
        "COMPACT"      | 0
        "NOTEXISTING"  | 100
        "invalid-name" | 100
    }

}
