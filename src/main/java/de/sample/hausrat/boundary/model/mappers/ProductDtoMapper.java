package de.sample.hausrat.boundary.model.mappers;

import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    ProductDto map(Product p);
    
    Product map(ProductDto p);

}
