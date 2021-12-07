package de.sample.hausrat.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import springfox.documentation.builders.OpenIdConnectSchemeBuilder;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.sample.hausrat.security.config.SecurityConstants.SECURITY_PROFILE;
import static java.util.List.of;

@Configuration
@Profile(SECURITY_PROFILE)
public class KeycloakOpenAPIConfig {

    @Value("${keycloak.auth-server-url}")
    String authServerUrl;
    @Value("${keycloak.realm}")
    String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Bean
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
    OperationBuilderPlugin securityNotesBuilder(final DescriptionResolver descriptions) {
        // reads out the @Secured annotation and prints a note into the Swagger UI.
        return new OperationBuilderPlugin() {

            @Override
            public boolean supports(DocumentationType delimiter) {
                return SwaggerPluginSupport.pluginDoesApply(delimiter);
            }

            @Override
            public void apply(OperationContext context) {
                context.findControllerAnnotation(Secured.class).map(Secured::value).ifPresent(authorities -> {
                    String sb = "\uD83D\uDD12 Accessible by users having one of the following roles: "
                      + "<b>"
                      + Arrays.stream(authorities)
                      .map(SecurityConstants.Roles::findByAuthority)
                      .filter(Optional::isPresent)
                      .map(Optional::get)
                      .map(SecurityConstants.Roles::getName)
                      .collect(Collectors.joining("</b>, <b>"))
                      + "</b>";
                    context.operationBuilder()
                      .summary("\uD83D\uDD12")
                      .notes(descriptions.resolve(sb));
                });
            }
        };
    }

    @Autowired
    void addSecurity(Docket docket) {
        docket
          .securitySchemes(of(authenticationScheme()))
          .securityContexts(of(securityContext()));
    }

    private String authorizationUrl() {
        return authServerUrl + "/realms/" + realm.replace(" ", "%20");
    }

    // Swagger UI 3.38.0 added support for OpenID Connect
    private springfox.documentation.service.SecurityScheme authenticationScheme() {
        return new OpenIdConnectSchemeBuilder()
          .name("keycloak")
          .description("Open ID with Keycloak")
          .openIdConnectUrl(authorizationUrl() + "/.well-known/openid-configuration")
          .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
          .securityReferences(defaultAuth())
          .operationSelector(operationContext ->
            operationContext.requestMappingPattern().startsWith("/api/")
          )
          .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return of(new SecurityReference("keycloak", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
          .clientId(clientId)
          .clientSecret(clientSecret)
          .realm(realm)
          .appName(clientId)
          .scopeSeparator(",")
          .additionalQueryStringParams(null)
          //.enableCsrfSupport(true)
          .useBasicAuthenticationWithAccessCodeGrant(false)
          .build();
    }
}
