package com.thuc.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountsDto {
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}",message = "AccountNumber must be 10 digits")
    private Long accountNumber;
    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;
    @NotEmpty(message = "Branch Address type can not be null or empty")
    private String branchAddress;
}
