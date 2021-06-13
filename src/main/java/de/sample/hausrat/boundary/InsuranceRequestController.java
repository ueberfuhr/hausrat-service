package de.sample.hausrat.boundary;

import de.sample.hausrat.control.InsuranceCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculations")
@RequiredArgsConstructor
public class InsuranceRequestController {

    private final InsuranceCalculationService service;

    //Collection<>
}
