package de.sample.hausrat.boundary.model.mappers;

import de.sample.hausrat.boundary.model.InsuranceCalculationResultDto;
import de.sample.hausrat.boundary.model.ProductDto;
import de.sample.hausrat.control.model.InsuranceCalculationResult;
import de.sample.hausrat.control.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = InsuranceCalculationRequestDtoMapper.class)
public interface InsuranceCalculationResultDtoMapper {

    InsuranceCalculationResultDto map(InsuranceCalculationResult p);

    // do not map the other side because products are read-only in the public API

}
