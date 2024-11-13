package org.example.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.accounts.dto.CustomerAccountDto;
import org.example.accounts.dto.CustomerDto;
import org.example.accounts.dto.ErrorResponseDto;
import org.example.accounts.dto.ResponseDto;
import org.example.accounts.service.AccountService;
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
}

