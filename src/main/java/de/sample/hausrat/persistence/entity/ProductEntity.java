package de.sample.hausrat.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Positive;

@Entity(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private String name;
    @Positive
    private int price;

}
