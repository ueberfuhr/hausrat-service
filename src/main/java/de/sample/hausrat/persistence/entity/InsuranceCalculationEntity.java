package de.sample.hausrat.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("insurancecalculation")
public class InsuranceCalculationEntity {

    @Id
    private Long id;

    /*
     * we do not register a foreign key here, because
     * products should be deletable without causing
     * inconsistency
     */
    @NotNull
    @Size(min = 1)
    private String product;

    @Positive
    private double livingArea;

    private String principal;

    @NotNull
    private BigDecimal value;

    @NotNull
    private String currency;

    @NotNull
    @PastOrPresent
    private LocalDateTime timestamp;
}
