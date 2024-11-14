package org.example.accounts.config;

import org.example.accounts.dto.AccountsContactInfoDto;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
public class ConfigProperties {
}
