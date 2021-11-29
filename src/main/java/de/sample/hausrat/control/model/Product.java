package de.sample.hausrat.control.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @NotNull
    private String name;
    @Positive
    private int price;

    // could be enhanced

}
