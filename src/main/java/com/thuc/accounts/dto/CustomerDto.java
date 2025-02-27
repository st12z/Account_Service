package com.thuc.accounts.dto;

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
public class CustomerDto {
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5,max = 30,message = "The length ò the customer name should be 5-30 ")
    private String name;
    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email is invalid")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
