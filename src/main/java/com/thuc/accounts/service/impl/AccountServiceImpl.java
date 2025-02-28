package com.thuc.accounts.service.impl;

import com.thuc.accounts.constants.AccountsConstant;
import com.thuc.accounts.dto.AccountsDto;
import com.thuc.accounts.dto.CustomerDto;
import com.thuc.accounts.entity.Accounts;
import com.thuc.accounts.entity.Customer;
import com.thuc.accounts.exception.CustomerAlreadyExistsException;
import com.thuc.accounts.exception.ResourceNotFoundException;
import com.thuc.accounts.mapper.AccountsMapper;
import com.thuc.accounts.mapper.CustomerMapper;
import com.thuc.accounts.repository.AccountsRepository;
import com.thuc.accounts.repository.CustomerRepository;
import com.thuc.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> existCustomer = customerRepository
                .findByMobileNumber(customerDto.getMobileNumber());
        if(existCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobileNumber: " + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.toCustomer(customerDto);
        Customer savedCustomer= customerRepository.save(customer);
        Accounts accounts = createNewAccount(savedCustomer);
        accountsRepository.save(accounts);
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(()->
                        new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
                );
        Accounts accounts = accountsRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Accounts","CustomerId",String.valueOf(customer.getCustomerId())));

        CustomerDto customerDto= CustomerMapper.toCustomerDto(customer);
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts));
        return customerDto;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccounts = new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextLong(900000000);
        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setAccountType(AccountsConstant.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstant.ADDRESS);
        return newAccounts;
    }
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts = accountsRepository
                    .findByAccountNumber(customerDto.getAccountsDto().getAccountNumber())
                    .orElseThrow(()-> new ResourceNotFoundException(
                            "Accounts",
                            "AccountNumber",
                            String.valueOf(customerDto.getAccountsDto().getAccountNumber())));
            accounts.setAccountType(accountsDto.getAccountType());
            accounts.setBranchAddress(accountsDto.getBranchAddress());
            accountsRepository.save(accounts);
            Customer customer = customerRepository
                    .findByMobileNumber(customerDto.getMobileNumber())
                    .orElseThrow(()-> new ResourceNotFoundException(
                            "Customer",
                            "mobileNumber",
                            customerDto.getMobileNumber()));
            customer.setEmail(customerDto.getEmail());
            customer.setMobileNumber(customerDto.getMobileNumber());
            customer.setName(customerDto.getName());
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;


    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Customer",
                        "mobileNumber",
                        mobileNumber));
        Accounts accounts = accountsRepository
                .findByCustomerId(customer.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Accounts","CustomerId",String.valueOf(customer.getCustomerId())));
        customerRepository.delete(customer);
        accountsRepository.delete(accounts);
        return true;
    }
}
