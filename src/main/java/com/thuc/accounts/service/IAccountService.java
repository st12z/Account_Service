package com.thuc.accounts.service;

import com.thuc.accounts.dto.CustomerDto;

public interface IAccountService {
    // create account
    void createAccount(CustomerDto customerDto);
    // fetch account
    CustomerDto fetchAccount(String mobileNumber);
    // update account
    boolean updateAccount(CustomerDto customerDto);
    // delete account
    boolean deleteAccount(String mobileNumber);
}
