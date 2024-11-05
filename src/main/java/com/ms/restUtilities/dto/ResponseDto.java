package com.ms.restUtilities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private Integer statusCode;
    private String statusMessage;
}
