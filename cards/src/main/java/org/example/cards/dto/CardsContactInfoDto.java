package org.example.cards.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "cards")
@Data
public class CardsContactInfoDto {
    private String message;
    private Map<String, String> contactDetails;
}
