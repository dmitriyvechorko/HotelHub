package org.hotelhub.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Schema(description = "Arrival time information")
public class ArrivalTime {

    @Column(name = "arrival_time_check_in", length = 5)
    @Schema(description = "Check-in time")
    private String checkIn;

    @Column(name = "arrival_time_check_out", length = 5)
    @Schema(description = "Check-out time")
    private String checkOut;
}