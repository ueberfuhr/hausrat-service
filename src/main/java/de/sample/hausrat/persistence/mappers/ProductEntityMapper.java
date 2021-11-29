package de.sample.hausrat.persistence.mappers;

import de.sample.hausrat.domain.model.Product;
import de.sample.hausrat.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    Product map(ProductEntity p);

    ProductEntity map(Product p);

}
