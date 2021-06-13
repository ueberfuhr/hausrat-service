package de.sample.hausrat.control.model.mappers;

import de.sample.hausrat.control.model.Product;
import de.sample.hausrat.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product map(ProductEntity p);

    ProductEntity map(Product p);

}
