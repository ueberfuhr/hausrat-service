# Linear Calculation

Feature: Linear Calculation Cases

  Background:
    Given we have a product named "COMPACT" with a single price of 650 "EUR"
    And we have a product named "OPTIMAL" with a single price of 700 "EUR"
    And the linear calculation

  Scenario: sample 1
    When the living area is 100 m²
    And the product is "COMPACT"
    Then the sum insured is 65000 "EUR"

  Scenario: sample 2
    When the living area is 100 m²
    And the product is "OPTIMAL"
    Then the sum insured is 70000 "EUR"

  Scenario: sample 3
    When the living area is 150 m²
    And the product is "COMPACT"
    Then the sum insured is 97500 "EUR"

  Scenario: sample 4
    When the living area is 150 m²
    And the product is "OPTIMAL"
    Then the sum insured is 105000 "EUR"

  Scenario: negative living area
    When the living area is -5 m²
    And the product is "OPTIMAL"
    Then the validation will fail

  Scenario: zero living area
    When the living area is 0 m²
    And the product is "OPTIMAL"
    Then the validation will fail

  Scenario: no product
    When the living area is 100 m²
    Then the validation will fail

  Scenario: product does not exist
    Given we don't have any product named "XYZ"
    When the living area is 100 m²
    And the product is "XYZ"
    Then the validation will fail

  Scenario: rounding sample
    When the living area is 33.45654 m²
    And the product is "OPTIMAL"
    Then the sum insured is 23419.58 "EUR"

