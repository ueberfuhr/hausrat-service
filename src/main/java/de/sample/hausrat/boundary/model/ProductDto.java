package de.sample.hausrat.boundary.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "Product", description = "The kind of insurance.")
@Data
public class ProductDto {

    @ApiModelProperty("The name of the product.")
    @NotNull
    private String name;

    // price is not part of the public api

    // could be enhanced, e.g. by description

}
