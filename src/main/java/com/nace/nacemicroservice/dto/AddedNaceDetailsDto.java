package com.nace.nacemicroservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Fozia
 */
@Data
@Builder
public class AddedNaceDetailsDto {

    private String status;
    private List<String> details;
    private LocalDateTime timeStamp;



}
