package de.sample.hausrat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @ProductName
    private String name;
    @Positive
    private int price;

    // could be enhanced

}
