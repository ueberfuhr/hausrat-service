package de.sample.hausrat.boundary.model.mappers;

import de.sample.hausrat.boundary.model.InsuranceCalculationRequestDto;
import de.sample.hausrat.control.model.InsuranceCalculationRequest;
import de.sample.hausrat.control.model.InsuranceCalculationResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InsuranceCalculationRequestDtoMapper {

    InsuranceCalculationRequestDto map(InsuranceCalculationRequest r);

    InsuranceCalculationRequest map(InsuranceCalculationRequestDto r);

}
