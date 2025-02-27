package com.thuc.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}
