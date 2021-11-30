package de.sample.hausrat.boundary.model;

import de.sample.hausrat.domain.model.ProductName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@ApiModel(value = "Product", description = "The kind of insurance.")
@Data
public class ProductDto {

    @ApiModelProperty("The name of the product.")
    @ProductName
    private String name;

    @ApiModelProperty("The single price unit.")
    @Positive
    private int price;

}
