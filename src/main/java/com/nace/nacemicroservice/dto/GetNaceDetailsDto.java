package com.nace.nacemicroservice.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Fozia
 */
@Data
@Builder
public class GetNaceDetailsDto {

    private Long order;
    private Long level;
    private String code;
    private String parent;
    private String description;
    private String itemIncludes;
    private String itemAlsoIncludes;
    private String rulings;
    private String itemExcludes;
    private String referencesIsic;
}
