package de.sample.hausrat.boundary


import de.sample.hausrat.boundary.model.ProductDto
import de.sample.hausrat.boundary.model.mappers.ProductDtoMapper
import de.sample.hausrat.domain.ProductService
import de.sample.hausrat.domain.model.Product
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import spock.lang.Specification

import java.util.stream.Stream

@WebMvcTest(controllers = ProductController.class)
class ProductControllerSpockTest extends Specification {

    @Autowired
    ProductController controller
    @SpringBean
    ProductService service = Mock()
    @SpringBean
    ProductDtoMapper mapper = Mock()

    def "when context is loaded then all expected beans are created"() {
        expect: "the product controller is created"
        controller
    }

    def "when service returns empty list, controller will return empty list too"() {
        when: "controller is used to find all products"
        def result = controller.getProducts()

        then: "result is empty"
        result.isEmpty()

        and: "result comes from service"
        1 * service.findAll() >> Stream.empty()
    }

    def "when service returns single element, controller will return single value too"() {
        given: "a sample product"
        def product = new Product()
        def productDto = new ProductDto()

        when: "controller is used to find all products"
        def result = controller.getProducts()

        then: "result has one single element"
        result?.size() == 1
        with result.find(), { it === productDto }

        and: "result comes from service"
        1 * service.findAll() >> Stream.of(product)

        and: "result was mapped by mapper"
        1 * mapper.map(product) >> productDto
    }

}
