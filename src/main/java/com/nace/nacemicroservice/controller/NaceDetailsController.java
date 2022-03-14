package com.nace.nacemicroservice.controller;

import com.nace.nacemicroservice.builder.ApiResponseBuilder;
import com.nace.nacemicroservice.constants.NaceDetailsConstants;

import com.nace.nacemicroservice.dto.AddedNaceDetailsDto;
import com.nace.nacemicroservice.dto.GetNaceDetailsDto;
import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import com.nace.nacemicroservice.exception.EntityNotFoundException;
import com.nace.nacemicroservice.filters.RequestCorrelation;
import com.nace.nacemicroservice.services.NaceDetailsService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;

/**
 * Controller for Nace Microservice.
 *
 * @author Fozia
 */

@RestController
@RequestMapping(NaceDetailsConstants.ENDPOINT_RESOURCE_NACE_V1)
@Validated
//@Api
public class NaceDetailsController {

    private static final Logger LOG = LoggerFactory.getLogger(NaceDetailsController.class);

    @Autowired
    NaceDetailsService naceService;

    @Autowired
    ApiResponseBuilder apiResponseBuilder;

    @GetMapping("/health")
    public ResponseEntity<String> chechHealth(){
        return new ResponseEntity<String>("Service is up", HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<AddedNaceDetailsDto> putNaceDetails(
            @RequestHeader(name = NaceDetailsConstants.FILE_PATH)
                @NotEmpty(message = "Value of request header named 'file-path' cannot be empty.")
                @NotBlank(message = "Value of request header named 'file-path' cannot be blank.")
                @Pattern(regexp = ".+(\\.csv)$",message = "File types other than CSV are not allowed.")
            final String filePath)
            throws NumberFormatException, IOException, ConstraintViolationException,InterruptedException {
        String corrId = RequestCorrelation.getId();
        LOG.info("[{}] NaceDetailsController | Import CSV | Start", corrId);
        List<NaceDetailsEntity> result = this.naceService.putNaceDetails(filePath.trim());
        ResponseEntity<AddedNaceDetailsDto> response = this.apiResponseBuilder.buildPostResponse(result);
        return response;
    }

    @GetMapping(NaceDetailsConstants.ENDPOINT_GET_NACE_DTLS)
    public ResponseEntity<List<GetNaceDetailsDto>> getNaceDetail (
            @PathVariable(name ="order", required = true)
            @NotEmpty(message = "Order value must not be empty in the api request url.")
            @NotBlank(message = "Order value must not be blank in the api request url.")
            @Digits(message = "Order value must be a non-fraction integer.", fraction = 0, integer = 10)
            @Min(value = 1, message = "Order value must be greater than zero.")
            final String order)
            throws EntityNotFoundException {
        List<NaceDetailsEntity> result = this.naceService.getNaceDetailsByOrder(Long.valueOf(order));
        return this.apiResponseBuilder.buildGetResponse(result);
    }


}
