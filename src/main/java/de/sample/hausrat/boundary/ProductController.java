package de.sample.hausrat.boundary;

import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.boundary.model.mappers.ProductDtoMapper;
import de.sample.hausrat.domain.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductDtoMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read all available products")
    @ApiResponses({ //
            @ApiResponse(code = 200, message = "The products were found and returned."), //
    })
    public Collection<ProductDto> getProducts() {
        return productService.findAll()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read product by name")
    @ApiResponses({ //
            @ApiResponse(code = 200, message = "The products was found and returned."), //
            @ApiResponse(code = 400, message = "The product name is not a single word."), //
            @ApiResponse(code = 404, message = "A product does not exist with the given name."), //
    })
    public ProductDto findByName(@PathVariable @Pattern(regexp = "\\w+") String name) {
        return productService.find(name)
                .map(mapper::map)
                .orElseThrow(NotFoundException::new);
    }

}
