package com.thuc.accounts.controller;

import com.thuc.accounts.constants.AccountsConstant;
import com.thuc.accounts.dto.CustomerDto;
import com.thuc.accounts.dto.ResponseDto;
import com.thuc.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountsController {
    private final IAccountService accountService;
    @PostMapping("/create")
    public ResponseEntity<?>  createAccount(@RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(AccountsConstant.STATUS_201)
                        .statusMsg(AccountsConstant.MESSAGE_201)
                        .build());
    }
    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAccountsDetails(
            @RequestParam String mobileNumber
    ) {
       CustomerDto customerDto= accountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.builder()
                        .statusCode(AccountsConstant.STATUS_200)
                        .statusMsg(AccountsConstant.MESSAGE_200)
                        .data(customerDto)
                        .build());
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .statusCode(AccountsConstant.STATUS_200)
                            .statusMsg(AccountsConstant.MESSAGE_200)
                            .build());
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder()
                            .statusMsg(AccountsConstant.MESSAGE_500)
                            .statusCode(AccountsConstant.STATUS_500)
                            .build())
                    ;
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestParam String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .statusCode(AccountsConstant.STATUS_200)
                            .statusMsg(AccountsConstant.MESSAGE_200)
                            .build());

        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder()
                            .statusMsg(AccountsConstant.MESSAGE_500)
                            .statusCode(AccountsConstant.STATUS_500)
                            .build());
        }
    }
}
