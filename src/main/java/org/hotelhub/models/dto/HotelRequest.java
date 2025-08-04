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
@Schema(description = "Request to create a new hotel")
public class HotelRequest {
    @Schema(description = "Hotel name")
    private String name;

    @Schema(description = "Hotel description")
    private String description;

    @Schema(description = "Hotel brand")
    private String brand;

    @Builder.Default
    @Schema(description = "Hotel address")
    private AddressRequest address = new AddressRequest();

    @Builder.Default
    @Schema(description = "Contact information")
    private ContactRequest contacts = new ContactRequest();

    @Builder.Default
    @Schema(description = "Arrival time information")
    private ArrivalTimeRequest arrivalTime = new ArrivalTimeRequest();
}


