package com.thuc.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name="Customer",
        description="Schema to hold Customer and Account information"
)
public class CustomerDto {
    @Schema(
            description = "Name of the customer",
            example = "Nguyen Tien Thuc"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5,max = 30,message = "The length ò the customer name should be 5-30 ")
    private String name;

    @Schema(
            description = "Email address of the customer",
            example = "thuc@gmail.com"
    )
    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email is invalid")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
