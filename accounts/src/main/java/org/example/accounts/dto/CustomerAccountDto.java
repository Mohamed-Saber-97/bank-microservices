package org.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(name = "Customer and Account",
        description = "Schema to hold Customer and Account Information")
@Builder
public record CustomerAccountDto(
        @NotEmpty(message = "Name can not be null or empty")
        @Size(min = 5,
              max = 100,
              message =
                      "The length of the " +
                              "customer name " + "should be" + " between 5 and 100")
        @Schema(description = "Name of the Customer",
                example = "Mohamed")
        String name,
        @NotEmpty(message = "Email can not be null or empty")
        @Email(message = "Email address " +
                "should have a " + "valid value")
        @Schema(description = "Email of the Customer",
                example = "user@user.com")
        String email,
        @NotEmpty(message = "Mobile number can not be null or empty")
        @Schema(description = "Mobile"
                + " Number of " + "the" + " Customer",
                example =
                        "01997332697")
        String mobileNumber,
        @Schema(description = "Bank Account"
                + " Number of " + "the" + " Customer",
                example =
                        "1")
        Long accountNumber,
        @NotEmpty(message = "Bank Account Type can not be null or empty")
        @Size(min = 5,
              max = 30,
              message =
                      "The length of the " +
                              "Account Type " + "should be" + " between 5 and 30")
        @Schema(
                description = "Bank Account Type",
                example = "Savings"
        )
        String accountType,
        @NotEmpty(message = "Branch Address can not be null or empty") @Size(min = 5,
                                                                             max = 255,
                                                                             message = "Branch " +
                                                                                     "Address " +
                                                                                     "length should" +
                                                                                     " be between 5" +
                                                                                     " and 255")
        @Schema(
                description = "Bank Address",
                example = "123 Street, London"
        )
        String branchAddress) {
    public static CustomerAccountDto fromCustomerAndAccount(CustomerDto customerDto, AccountsDto accountsDto) {
        return new CustomerAccountDto(customerDto.name(),
                                      customerDto.email(),
                                      customerDto.mobileNumber(),
                                      accountsDto.accountNumber(),
                                      accountsDto.accountType(),
                                      accountsDto.branchAddress());
    }
}
