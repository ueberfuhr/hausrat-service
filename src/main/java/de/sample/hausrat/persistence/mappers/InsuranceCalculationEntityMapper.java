package de.sample.hausrat.persistence.mappers;

import de.sample.hausrat.domain.model.InsuranceCalculationResult;
import de.sample.hausrat.persistence.entity.InsuranceCalculationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InsuranceCalculationEntityMapper {

    @Mapping(source = "product", target = "request.product")
    @Mapping(source = "livingArea", target = "request.livingArea")
    @Mapping(target = "request", ignore = true)
    InsuranceCalculationResult map(InsuranceCalculationEntity entity);

    @InheritInverseConfiguration
    InsuranceCalculationEntity map(InsuranceCalculationResult entity);

}
