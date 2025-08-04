package org.hotelhub.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to add amenities to a hotel")
public class AmenitiesRequest {
    @Schema(description = "List of amenities to add")
    private List<String> amenities;
}