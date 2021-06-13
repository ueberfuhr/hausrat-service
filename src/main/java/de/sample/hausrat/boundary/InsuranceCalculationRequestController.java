package de.sample.hausrat.boundary;

import de.sample.hausrat.boundary.model.InsuranceCalculationRequestDto;
import de.sample.hausrat.boundary.model.InsuranceCalculationResultDto;
import de.sample.hausrat.boundary.model.mappers.InsuranceCalculationRequestDtoMapper;
import de.sample.hausrat.boundary.model.mappers.InsuranceCalculationResultDtoMapper;
import de.sample.hausrat.control.InsuranceCalculationService;
import de.sample.hausrat.control.model.InsuranceCalculationResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/calculations")
@RequiredArgsConstructor
@Validated
public class InsuranceCalculationRequestController {

    private final InsuranceCalculationService service;
    private final InsuranceCalculationRequestDtoMapper requestMapper;
    private final InsuranceCalculationResultDtoMapper resultMapper;

    // TODO add pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read the calculations that were done before.")
    @ApiResponses({ //
            @ApiResponse(code = 200, message = "The calculation results were found and returned."), //
    })
    // TODO we should use pagination here
    public Collection<InsuranceCalculationResultDto> findAll() {
        return service.findAll()
                .map(resultMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Read a single calculation that was done before.")
    @ApiResponses({ //
            @ApiResponse(code = 200, message = "The calculation result was found and returned."), //
            @ApiResponse(code = 400, message = "The id is invalid."), //
            @ApiResponse(code = 404, message = "A calculation with the given id could not be found."), //
    })
    public InsuranceCalculationResultDto findById(@PathVariable @PositiveOrZero long id) {
        return service.findById(id)
                .map(resultMapper::map)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Executes a calculation and persists the result.")
    @ApiResponses({ //
            @ApiResponse(code = 201, message = "The calculation was processed successfully and the result is returned."), //
            @ApiResponse(code = 400, message = "The request is invalid."), //
    })
    // TODO maybe we could implement this the reactive way?
    public ResponseEntity<InsuranceCalculationResultDto> executeCalculation(
            @Valid @RequestBody InsuranceCalculationRequestDto request) {
        InsuranceCalculationResult result = service.process(requestMapper.map(request));
        URI location = linkTo(methodOn(InsuranceCalculationRequestController.class).findById(result.getId())).toUri();
        return ResponseEntity.created(location).body(resultMapper.map(result));
    }

}
