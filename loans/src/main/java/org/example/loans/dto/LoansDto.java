package org.example.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Schema(name = "Loans",
        description = "Schema to hold Loan information"
)
@Builder
public record LoansDto(@NotEmpty(message = "Mobile Number can not be a null or empty")
                       @Pattern(regexp = "(^$|[0-9]{10})",
                                message = "Mobile Number must be 10 digits")
                       @Schema(
                               description = "Mobile Number of Customer",
                               example = "4354437687"
                       ) String mobileNumber,
                       @NotEmpty(message = "Loan Number can not be a null or empty")
                       @Pattern(regexp = "(^$|[0-9]{12})",
                                message = "LoanNumber must be 12 digits")
                       @Schema(
                               description = "Loan Number of the customer",
                               example = "548732457654"
                       )
                       String loanNumber,

                       @NotEmpty(message = "LoanType can not be a null or empty")
                       @Schema(
                               description = "Type of the loan",
                               example = "Home Loan"
                       )
                       String loanType,

                       @Positive(message = "Total loan amount should be greater than zero")
                       @Schema(
                               description = "Total loan amount",
                               example = "100000"
                       )
                       Integer totalLoan,

                       @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
                       @Schema(
                               description = "Total loan amount paid",
                               example = "1000"
                       )
                       Integer amountPaid,

                       @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
                       @Schema(
                               description = "Total outstanding amount against a loan",
                               example = "99000"
                       )
                       Integer outstandingAmount) {


}