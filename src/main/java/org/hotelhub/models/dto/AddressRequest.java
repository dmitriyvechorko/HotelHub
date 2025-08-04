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
@Schema(description = "Address information")
public class AddressRequest {
    @Schema(description = "House number")
    private Integer houseNumber;

    @Schema(description = "Street name")
    private String street;

    @Schema(description = "City")
    private String city;

    @Schema(description = "Country")
    private String country;

    @Schema(description = "Postal code")
    private String postCode;
}