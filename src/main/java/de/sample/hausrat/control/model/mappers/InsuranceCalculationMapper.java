package de.sample.hausrat.control.model.mappers;

import de.sample.hausrat.control.model.InsuranceCalculationResult;
import de.sample.hausrat.entity.InsuranceCalculationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface InsuranceCalculationMapper {

    @Mappings({
            @Mapping(source = "product", target = "request.product"),
            @Mapping(source = "livingArea", target = "request.livingArea"),
            @Mapping(target = "request", ignore = true)
    })
    InsuranceCalculationResult map(InsuranceCalculationEntity entity);

    @InheritInverseConfiguration
    InsuranceCalculationEntity map(InsuranceCalculationResult entity);

}
