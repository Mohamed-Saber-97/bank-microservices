package org.example.loans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.example.loans.dto.ErrorResponseDto;
import org.example.loans.dto.LoansContactInfoDto;
import org.example.loans.dto.LoansDto;
import org.example.loans.dto.ResponseDto;
import org.example.loans.service.LoansService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Loans in Banking Application",
        description = "CRUD REST APIs in Banking Application to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api/v1",
                produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class LoansController {

    private final LoansService loansService;
    @Value("${build.version}")
    private String buildVersion;
    private final LoansContactInfoDto loansContactInfoDto;
    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside Banking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})",
                     message = "Mobile number must be 10 digits")
            String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.fromHttpStatus(HttpStatus.CREATED));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})",
                     message = "Mobile number must be 10 digits")
            String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(loansDto);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(
            @Valid
            @RequestBody
            LoansDto loansDto) {
        boolean isUpdated = loansService.updateLoan(loansDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.fromHttpStatus(HttpStatus.OK));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseDto.fromHttpStatus(HttpStatus.EXPECTATION_FAILED));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})",
                     message = "Mobile number must be 10 digits")
            String mobileNumber) {
        boolean isDeleted = loansService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.fromHttpStatus(HttpStatus.OK));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseDto.fromHttpStatus(HttpStatus.EXPECTATION_FAILED));
        }
    }
    @Operation(summary = "Get Build Information",
               description = "Get Build Information that is deployed inside the microservice application")
    @ApiResponses({@ApiResponse(responseCode = "200",
                                description = "HttpStatus OK",
                                content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "HttpStatus INTERNAL SERVER ERROR",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(buildVersion);
    }

    @Operation(summary = "Get Contact Information",
               description = "Contact Info details that can be reached out in case of any issues")
    @ApiResponses({@ApiResponse(responseCode = "200",
                                description = "HttpStatus OK",
                                content = @Content(schema = @Schema(implementation = LoansContactInfoDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "HttpStatus INTERNAL SERVER ERROR",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(loansContactInfoDto);
    }
}