package de.sample.hausrat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Price {

    private BigDecimal value;
    private String currency;

}
