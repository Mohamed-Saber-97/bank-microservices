package org.example.loans.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "loans")
@Data
public class LoansContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
}
