package org.hotelhub.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hotelhub.models.entities.Address;
import org.hotelhub.models.entities.ArrivalTime;
import org.hotelhub.models.entities.Contact;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed hotel information")
public class HotelResponse {

    @Schema(description = "Hotel ID")
    private Long id;

    @Schema(description = "Hotel name")
    private String name;

    @Schema(description = "Hotel description")
    private String description;

    @Schema(description = "Hotel brand")
    private String brand;

    @Schema(description = "Hotel address")
    private Address address;

    @Schema(description = "Contact information")
    private Contact contacts;

    @Schema(description = "Arrival time information")
    private ArrivalTime arrivalTime;

    @Schema(description = "List of amenities")
    private List<String> amenities;
}
