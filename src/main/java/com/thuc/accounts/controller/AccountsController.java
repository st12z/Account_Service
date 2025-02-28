package com.thuc.accounts.controller;

import com.thuc.accounts.constants.AccountsConstant;
import com.thuc.accounts.dto.AccountsDto;
import com.thuc.accounts.dto.CustomerDto;
import com.thuc.accounts.dto.ErrorResponseDto;
import com.thuc.accounts.dto.ResponseDto;
import com.thuc.accounts.entity.Accounts;
import com.thuc.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(
        name="CRUD REST APIS for Accounts in Eazybank",
        description = "CRUD REST APIs in EazyBank to CREATE,UPDATE,FETCH AND DELETE accounts details"
)
public class AccountsController {
    private final IAccountService accountService;
    @Operation(method = "POST",
            summary = "Create account",
            description = "REST API to create new Customer & Account inside EazyBank",tags = {"accounts"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "account is created successfully",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "data send to server not invalid",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))})
            }
    )
    @PostMapping("/create")
    public ResponseEntity<?>  createAccount(@RequestBody @Valid CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(AccountsConstant.STATUS_201)
                        .statusMsg(AccountsConstant.MESSAGE_201)
                        .build());
    }
    @Operation(summary = "fetch accounts",description = "",tags = {"accounts"})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "get accounts detail successfully",
                            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ResponseDto.class))}
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "accounts not found",
                            content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponseDto.class))}
                    )
            }
    )
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
    public ResponseEntity<?> updateAccount(@RequestBody @Valid CustomerDto customerDto) {
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
