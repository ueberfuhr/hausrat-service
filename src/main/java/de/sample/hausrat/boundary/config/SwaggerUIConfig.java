package de.sample.hausrat.boundary.config;

import de.sample.hausrat.InsuranceApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.List.of;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@EnableOpenApi
@Configuration
class SwaggerUIConfig {

    // http://localhost:8080/swagger-ui/

    @Bean
    Docket api() {
        return new Docket(DocumentationType.OAS_30)
          .useDefaultResponseMessages(false)
          .securitySchemes(of())
          .select()
          .apis(basePackage(InsuranceApplication.class.getPackage().getName()))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Insurance Calculation Service")
          .description("A service providing insurance calculations.")
          .version("1.0")
          .build();
    }

}
