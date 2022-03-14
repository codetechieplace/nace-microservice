package com.nace.nacemicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fozia
 */
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private String message;
    private List<String> details;
    private LocalDateTime time;

}
