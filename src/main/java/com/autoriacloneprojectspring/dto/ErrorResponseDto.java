package com.autoriacloneprojectspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private Integer status;
    private String message;
}
