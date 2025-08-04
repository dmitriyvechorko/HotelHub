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
@Schema(description = "Contact information")
public class ContactRequest {

    @Schema(description = "Phone number")
    private String phone;

    @Schema(description = "Email address")
    private String email;
}

