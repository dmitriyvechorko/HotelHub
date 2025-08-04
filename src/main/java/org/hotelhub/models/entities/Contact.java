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
@Schema(description = "Contact information")
public class Contact {

    @Column(name = "contacts_phone", length = 30)
    @Schema(description = "Phone number")
    private String phone;

    @Column(name = "contacts_email")
    @Schema(description = "Email address")
    private String email;
}
