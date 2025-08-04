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
@Schema(description = "Arrival time information")
public class ArrivalTimeRequest {

    @Schema(description = "Check-in time")
    private String checkIn;

    @Schema(description = "Check-out time")
    private String checkOut;
}
