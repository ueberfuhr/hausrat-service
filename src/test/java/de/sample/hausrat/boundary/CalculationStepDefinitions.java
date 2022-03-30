package de.sample.hausrat.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sample.hausrat.InsuranceApplication;
import de.sample.hausrat.boundary.model.InsuranceCalculationRequestDto;
import de.sample.hausrat.boundary.model.InsuranceCalculationResultDto;
import de.sample.hausrat.config.properties.CalculationCurrencyProperties;
import de.sample.hausrat.domain.ProductService;
import de.sample.hausrat.domain.model.Product;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CucumberContextConfiguration
@SpringBootTest(classes = InsuranceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CalculationStepDefinitions {

    @Autowired
    MockMvc mvc; // testing by sending HTTP requests and verifying HTTP responses
    @Autowired
    ObjectMapper mapper; // used to render or parse JSON
    @Autowired
    ProductService productService;
    @Autowired
    private CalculationCurrencyProperties currencyProperties;

    private String product;
    private double livingArea;

    @Given("the linear calculation")
    public void setup() {
        // nothing to do here - we currently do not support further calculations
    }

    @Given("we have a product named {string} with a single price of {int} {string}")
    public void let_product_exist(String name, int price, String currencyCode) {
        assertThat(currencyCode).isEqualTo(this.currencyProperties.getCode());
        productService.save(new Product(name, price));
    }

    @Given("we don't have any product named {string}")
    public void let_product_not_exist(String name) {
        productService.find(name)
          .map(Product::getName)
          .ifPresent(productService::delete);
    }

    @When("the product is {string}")
    public void the_product_is(String product) {
        this.product = product;
    }

    @When("the living area is {float} m²")
    public void the_living_area_is(double livingArea) {
        this.livingArea = livingArea;
    }

    private ResultActions executeCalculation() throws Exception {
        InsuranceCalculationRequestDto dto = new InsuranceCalculationRequestDto();
        dto.setProduct(this.product);
        dto.setLivingArea(this.livingArea);
        return mvc
          .perform(
            post("/api/v1/calculations")
              .contentType(MediaType.APPLICATION_JSON)
              .content(mapper.writeValueAsString(dto))
          );
    }

    private InsuranceCalculationResultDto executeCalculationSuccessful() throws Exception {
        String body = executeCalculation()
          .andExpect(status().isCreated())
          .andExpect(header().exists(HttpHeaders.LOCATION))
          .andReturn().getResponse().getContentAsString();
        return mapper.readValue(body, InsuranceCalculationResultDto.class);
    }

    @Then("the sum insured is {float} {string}")
    public void the_sum_insured_is(double value, String currencyCode) throws Exception {
        assertThat(currencyCode).isEqualTo(this.currencyProperties.getCode());
        InsuranceCalculationResultDto result = executeCalculationSuccessful();
        BigDecimal expectedValue = BigDecimal.valueOf(value)
          .setScale(this.currencyProperties.getPrecision(), RoundingMode.HALF_UP);
        assertAll(
          () -> assertThat(result.getValue()).isEqualTo(expectedValue),
          () -> assertThat(result.getCurrency()).isEqualTo(currencyCode)
        );
    }

    @Then("the validation will fail")
    public void the_validation_will_fail() throws Exception {
        executeCalculation()
          .andExpect(status().isBadRequest());
    }

}
