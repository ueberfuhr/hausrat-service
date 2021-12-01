package de.sample.hausrat.boundary;

import de.sample.hausrat.boundary.config.Authorities;
import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.boundary.model.mappers.ProductDtoMapper;
import de.sample.hausrat.domain.ProductService;
import de.sample.hausrat.domain.model.ProductName;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
@Secured(Authorities.AGENT)
public class ProductController {

    private final ProductService productService;
    private final ProductDtoMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read all available products")
    @ApiResponses({
      @ApiResponse(code = 200, message = "The products were found and returned."),
    })
    public Collection<ProductDto> getProducts() {
        return productService.findAll().map(mapper::map).collect(Collectors.toList());
    }

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read product by name")
    @ApiResponses({
      @ApiResponse(code = 200, message = "The products was found and returned."),
      @ApiResponse(code = 400, message = "The product name is not a single word containing uppercase letters and digits."),
      @ApiResponse(code = 404, message = "A product does not exist with the given name."),
    })
    public ProductDto findByName(@PathVariable @ProductName String name) {
        return productService.find(name).map(mapper::map).orElseThrow(NotFoundException::new);
    }

    @PutMapping(value = "{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Creates or updates a product.")
    @ApiResponses({
      @ApiResponse(code = 204, message = "The product was successfully created or updated."),
      @ApiResponse(code = 400, message = "The product name is not a single word containing uppercase letters and digits, or the submitted product's name does not match the URL of the resource."),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createProduct(@PathVariable @ProductName String name, @Valid ProductDto dto) {
        if (!name.equals(dto.getName())) {
            throw new ValidationException("product name is not matching the resource's URL");
        }
        productService.save(mapper.map(dto));
    }

    @DeleteMapping("{name}")
    @ApiOperation("Deletes a product.")
    @ApiResponses({
      @ApiResponse(code = 204, message = "The product was successfully deleted."),
      @ApiResponse(code = 400, message = "The product name is not a single word containing uppercase letters and digits."),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable @ProductName String name) {
        productService.delete(name);
    }

}
