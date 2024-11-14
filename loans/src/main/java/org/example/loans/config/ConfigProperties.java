package org.example.loans.config;

import org.example.loans.dto.LoansContactInfoDto;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
public class ConfigProperties {
}
