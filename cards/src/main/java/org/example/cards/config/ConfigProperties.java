package org.example.cards.config;

import org.example.cards.dto.CardsContactInfoDto;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
public class ConfigProperties {
}
