package org.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Schema(name = "HTTP Response",
        description = "Schema to view HTTP Response Information")
@Builder
public record ResponseDto(@Schema(description = "HTTP value (e.g., 200, 201, 404)",
                                  example = "200")
                          int value,
                          @Schema(description = "HTTP Series (e.g., INFORMATIONAL, SUCCESSFUL, REDIRECTION, " +
                                  "CLIENT_ERROR, SERVER_ERROR)",
                                  example = "SUCCESSFUL") HttpStatus.Series series,
                          @Schema(description = "HTTP Reason (e.g., OK, Created, Accepted)",
                                  example = "Ok") String reason) {
    public static ResponseDto fromHttpStatus(HttpStatus status) {
        return new ResponseDto(status.value(), status.series(), status.getReasonPhrase());
    }
}