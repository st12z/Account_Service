package com.thuc.accounts.mapper;

import com.thuc.accounts.dto.AccountsDto;
import com.thuc.accounts.entity.Accounts;

public class AccountsMapper {
    public static AccountsDto mapToAccountsDto(Accounts accounts) {
        return AccountsDto.builder()
                .accountNumber(accounts.getAccountNumber())
                .accountType(accounts.getAccountType())
                .branchAddress(accounts.getBranchAddress())
                .build();
    }
    public static Accounts mapToAccounts(AccountsDto dto) {
        return Accounts.builder()
                .accountNumber(dto.getAccountNumber())
                .accountType(dto.getAccountType())
                .branchAddress(dto.getBranchAddress())
                .build();
    }
}
