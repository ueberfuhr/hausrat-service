package de.sample.hausrat.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Positive;

@Data
@Table("product")
public class ProductEntity implements Persistable<String> {

    @Id
    private String name;
    @Positive
    private int price;
    @Transient
    private boolean alreadyExisting = true;

    @Override
    @Transient
    public String getId() {
        return name;
    }

    @Override
    @Transient
    public boolean isNew() {
        return !this.isAlreadyExisting();
    }
    public ProductEntity setAsNew() {
        this.alreadyExisting = false;
        return this;
    }
}
