package org.example.accounts.mapper;

import org.example.accounts.dto.AccountsDto;
import org.example.accounts.entity.Accounts;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountsMapper {
    Accounts toEntity(AccountsDto accountsDto);

    AccountsDto toDto(Accounts accounts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Accounts partialUpdate(
            AccountsDto accountsDto,
            @MappingTarget Accounts accounts);
}