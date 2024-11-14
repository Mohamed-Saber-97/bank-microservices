package org.example.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accounts.dto.*;
import org.example.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CRUD REST APIs for Accounts in Banking Application",
     description = "CRUD REST APIs for Accounts in Banking Application to CREATE, UPDATE, FETCH, DELETE account " +
             "details")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1",
                produces = {MediaType.APPLICATION_JSON_VALUE})
@Valid
public class AccountController {
    private final AccountService accountService;
    @Value("${build.version}")
    private String buildVersion;
    private final AccountsContactInfoDto accountsContactInfoDto;

    @Operation(summary = "Create Account REST API",
               description = "REST API to create new Customer and Account inside Banking Application")
    @ApiResponse(responseCode = "201",
                 description = "HttpStatus CREATED",
                 content = @Content(
                         schema = @Schema(implementation = ResponseDto.class)
                 ))
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(
            @Valid
            @RequestBody
            CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(ResponseDto.fromHttpStatus(HttpStatus.CREATED));
    }

    @Operation(summary = "Fetch Account REST API",
               description = "REST API to fetch Customer and Account details based on mobile number")
    @ApiResponse(responseCode = "302",
                 description = "HttpStatus FOUND",
                 content = @Content(schema = @Schema(implementation = CustomerAccountDto.class)))
    @GetMapping("/fetch")
    public ResponseEntity<CustomerAccountDto> fetchAccountDetails(
            @RequestParam
            String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountService.fetchAccountByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.FOUND)
                             .body(customerAccountDto);
    }

    @Operation(summary = "Update Account REST API",
               description = "REST API to Update Customer and Account details based on Bank Account Number")
    @ApiResponse(responseCode = "200",
                 description = "HttpStatus OK",
                 content = @Content(schema = @Schema(implementation = CustomerAccountDto.class)))
    @PatchMapping("/update")
    public ResponseEntity<CustomerAccountDto> updateAccount(
            @Valid
            @RequestBody
            CustomerAccountDto customerAccountDto) {
        CustomerAccountDto updatedCustomerAccountDto = accountService.updateAccount(customerAccountDto);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(updatedCustomerAccountDto);
    }

    @Operation(summary = "Delete Account REST API",
               description = "REST API to Delete Customer and Account details based on Mobile Number")
    @ApiResponses({@ApiResponse(responseCode = "204",
                                description = "HttpStatus NO CONTENT",
                                content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "HttpStatus INTERNAL SERVER ERROR",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam
            String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(ResponseDto.fromHttpStatus(HttpStatus.NO_CONTENT));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ResponseDto.fromHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR));

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
                                content = @Content(schema = @Schema(implementation = AccountsContactInfoDto.class))),
            @ApiResponse(responseCode = "500",
                         description = "HttpStatus INTERNAL SERVER ERROR",
                         content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))

    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(accountsContactInfoDto);
    }
}

