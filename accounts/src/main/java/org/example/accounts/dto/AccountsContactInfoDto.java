package org.example.accounts.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
@Data
public class AccountsContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
}
