package de.sample.hausrat.boundary.model.mappers;

import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.control.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    ProductDto map(Product p);

    // do not map the other side because products are read-only in the public API

}
