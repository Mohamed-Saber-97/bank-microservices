package org.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link org.example.accounts.entity.Accounts}
 */
@Schema(name = "Account",
        description = "Schema to hold Account Information")
@Builder
public record AccountsDto(Long accountNumber,
                          @NotEmpty(message = "Account Type can not be null or empty") String accountType,
                          @NotEmpty(message = "Branch Address can not be null or empty") @Size(min = 5,
                                                                                               max = 255,
                                                                                               message = "Branch " +
                                                                                                       "Address " +
                                                                                                       "length should" +
                                                                                                       " be between 5" +
                                                                                                       " and 255") String branchAddress) implements Serializable {
}