package com.thuc.accounts.mapper;

import com.thuc.accounts.dto.CustomerDto;
import com.thuc.accounts.entity.Customer;

public class CustomerMapper {
    public static CustomerDto toCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .mobileNumber(String.valueOf(customer.getMobileNumber()))
                .build();
    }
    public static Customer toCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .email(customerDto.getEmail())
                .name(customerDto.getName())
                .mobileNumber(String.valueOf(customerDto.getMobileNumber()))
                .build();
    }
}
