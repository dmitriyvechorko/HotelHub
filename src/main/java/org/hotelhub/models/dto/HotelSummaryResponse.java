package org.hotelhub.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Summary hotel information")
public class HotelSummaryResponse {

    @Schema(description = "Hotel ID")
    private Long id;

    @Schema(description = "Hotel name")
    private String name;

    @Schema(description = "Hotel description")
    private String description;

    @Schema(description = "Full address")
    private String address;

    @Schema(description = "Contact phone number", example = "+375 17 309-80-00")
    private String phone;
}