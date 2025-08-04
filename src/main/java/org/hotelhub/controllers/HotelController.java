package org.hotelhub.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hotelhub.models.dto.*;
import org.hotelhub.services.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotel Management", description = "Operations for hotel management")
public class HotelController {

    private final HotelService hotelService;

    @Operation(
            summary = "Get all hotels",
            description = "Retrieve a list of all hotels with summary information",
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
    public ResponseEntity<List<HotelSummaryResponse>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotelsSummary());
    }

    @Operation(
            summary = "Get hotel by ID",
            description = "Retrieve detailed information for a specific hotel",
            parameters = {
                    @Parameter(name = "id", description = "Hotel ID", example = "1")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HotelResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Hotel not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @Operation(
            summary = "Create a new hotel",
            description = "Create a new hotel with the provided details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Hotel data to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = HotelRequest.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Hotel created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = HotelSummaryResponse.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<HotelSummaryResponse> createHotel(
            @RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.createHotel(request));
    }

    @Operation(
            summary = "Add amenities to hotel",
            description = "Add amenities to an existing hotel",
            parameters = {
                    @Parameter(name = "id", description = "Hotel ID", example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of amenities to add",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AmenitiesRequest.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Amenities added successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Hotel not found"
                    )
            }
    )
    @PostMapping("/{id}/amenities")
    public ResponseEntity<Void> addAmenities(
            @PathVariable Long id,
            @RequestBody AmenitiesRequest request) {

        hotelService.addAmenities(id, request);
        return ResponseEntity.ok().build();
    }
}