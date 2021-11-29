package de.sample.hausrat.boundary

import de.sample.hausrat.InsuranceApplication
import de.sample.hausrat.boundary.model.mappers.ProductDtoMapper
import de.sample.hausrat.control.ProductService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.util.stream.Stream

@SpringBootTest(classes = InsuranceApplication.class)
class ProductControllerSpockTest extends Specification {

    @Autowired
    ProductController controller;
    @SpringBean
    ProductService service = Mock();
    @SpringBean
    ProductDtoMapper mapper = Mock();

    def "when context is loaded then all expected beans are created"() {
        expect: "the product controller is created"
        controller.productService
        controller.mapper
    }

    def "when service returns empty result, controller will return empty result too"() {
        when: "controller is used to find all products"
        def result = controller.findAll()
        then: "result is empty"
        result.isEmpty()
        and: "result comes from service"
        1 * service.findAll() >> Stream.empty()
    }

}
