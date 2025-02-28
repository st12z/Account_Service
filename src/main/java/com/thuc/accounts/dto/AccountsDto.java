package com.thuc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name="Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {
    @NotEmpty(message = "Account number can not be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}",message = "AccountNumber must be 10 digits")
    @Schema(
            description = "Account Number"
    )
    private Long accountNumber;

    @Schema(
            description = "Account Type",
            example = "SAVINGS"
    )
    @NotEmpty(message = "Account type can not be null or empty")
    private String accountType;

    @Schema(
            description = "Account Branch Address",
            example = "New York"
    )
    @NotEmpty(message = "Branch Address type can not be null or empty")
    private String branchAddress;
}
