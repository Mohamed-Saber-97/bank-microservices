package org.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link org.example.accounts.entity.Customer}
 */
@Schema(name = "Customer",
        description = "Schema to hold Customer")
@Builder
public record CustomerDto(@NotEmpty(message = "Name can not be null or empty")
                          @Size(min = 5,
                                max = 100,
                                message =
                                        "The length of the " +
                                                "customer name " + "should be" + " between 5 and 100")
                          @Schema(description = "Name of the Customer",
                                  example = "Mohamed") String name,
                          @NotEmpty(message = "Email can not be null or empty")
                          @Email(message = "Email address " +
                                  "should have a " + "valid value")
                          @Schema(description = "Email of the Customer",
                                  example = "user@user.com") String email,
                          @NotEmpty(message = "Mobile number can not be null or empty")
                          @Schema(description = "Mobile"
                                  + " Number of " + "the" + " Customer",
                                  example =
                                          "01997332697") String mobileNumber) implements Serializable {
}