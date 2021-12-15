package de.sample.hausrat.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sample.hausrat.InsuranceApplication;
import de.sample.hausrat.boundary.model.InsuranceCalculationRequestDto;
import de.sample.hausrat.boundary.model.InsuranceCalculationResultDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.r2dbc.AutoConfigureDataR2dbc;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
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
@AutoConfigureWebFlux
@DataR2dbcTest
public class CalculationStepDefinitions {

    @Autowired
    MockMvc mvc; // testing by sending HTTP requests and verifying HTTP responses
    @Autowired
    ObjectMapper mapper; // used to render or parse JSON
    @Value("${calculation.currency.precision:2}")
    private int currencyPrecision;

    private String product;
    private double livingArea;

    @Given("the linear calculation")
    public void setup() {
        // nothing to do here - we currently do not support further calculations
    }

    @When("the product is \\\"([^\\\"]*)\\\"$")
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
        InsuranceCalculationResultDto result = executeCalculationSuccessful();
        BigDecimal expectedValue = BigDecimal.valueOf(value)
                .setScale(this.currencyPrecision, RoundingMode.HALF_UP);
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
