package org.hotelhub.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Address details")
public class Address {

    @Column(name = "address_house_number")
    @Schema(description = "House number")
    private Integer houseNumber;

    @Column(name = "address_street")
    @Schema(description = "Street name")
    private String street;

    @Column(name = "address_city")
    @Schema(description = "City")
    private String city;

    @Column(name = "address_country")
    @Schema(description = "Country")
    private String country;

    @Column(name = "address_post_code")
    @Schema(description = "Postal code")
    private String postCode;
}