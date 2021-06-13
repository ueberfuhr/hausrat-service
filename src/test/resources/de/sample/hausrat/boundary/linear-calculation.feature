# Linear Calculation
# Tags: optional

Feature: Linear Calculation

  Scenario: sample 1
    Given the linear calculation
    When the living area is 100 m²
    And the product is "COMPACT"
    Then the sum insured is 65000 "EUR"

  Scenario: sample 2
    Given the linear calculation
    When the living area is 100 m²
    And the product is "OPTIMAL"
    Then the sum insured is 70000 "EUR"

  Scenario: sample 3
    Given the linear calculation
    When the living area is 150 m²
    And the product is "COMPACT"
    Then the sum insured is 97500 "EUR"

  Scenario: sample 4
    Given the linear calculation
    When the living area is 150 m²
    And the product is "OPTIMAL"
    Then the sum insured is 105000 "EUR"

  Scenario: negative living area
    Given the linear calculation
    When the living area is -5 m²
    And the product is "OPTIMAL"
    Then the validation will fail

  Scenario: zero living area
    Given the linear calculation
    When the living area is 0 m²
    And the product is "OPTIMAL"
    Then the validation will fail

  Scenario: no product
    Given the linear calculation
    When the living area is 100 m²
    Then the validation will fail

  Scenario: rounding sample
    Given the linear calculation
    When the living area is 33.45654 m²
    And the product is "OPTIMAL"
    Then the sum insured is 23419.58 "EUR"

