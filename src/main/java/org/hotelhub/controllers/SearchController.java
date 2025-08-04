package org.hotelhub.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hotelhub.models.dto.HotelSummaryResponse;
import org.hotelhub.services.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/property-view/search")
@RequiredArgsConstructor
@Tag(name = "Hotel Search", description = "Operations for searching hotels")
public class SearchController {

    private final HotelService hotelService;

    @Operation(
            summary = "Search hotels",
            description = "Search hotels by various criteria",
            parameters = {
                    @Parameter(name = "name", description = "Hotel name", example = "Hilton"),
                    @Parameter(name = "brand", description = "Hotel brand", example = "Hilton"),
                    @Parameter(name = "city", description = "City", example = "Minsk"),
                    @Parameter(name = "country", description = "Country", example = "Belarus"),
                    @Parameter(name = "amenities", description = "Amenity", example = "Free WiFi")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HotelSummaryResponse.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<HotelSummaryResponse>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {

        return ResponseEntity.ok(hotelService.searchHotels(
                name, brand, city, country, amenities));
    }
}