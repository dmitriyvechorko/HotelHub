package org.hotelhub.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hotelhub.services.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/property-view/histogram")
@RequiredArgsConstructor
@Tag(name = "Hotel Statistics", description = "Operations for hotel statistics")
public class HistogramController {

    private final HotelService hotelService;

    @Operation(
            summary = "Get histogram data",
            description = "Get histogram data grouped by specified parameter",
            parameters = {
                    @Parameter(
                            name = "param",
                            description = "Grouping parameter (brand, city, country, amenities)",
                            example = "city",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameter"
                    )
            }
    )
    @GetMapping("/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param) {
        return ResponseEntity.ok(hotelService.getHistogram(param));
    }
}