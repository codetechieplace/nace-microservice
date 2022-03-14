package com.nace.nacemicroservice.builder;

import com.nace.nacemicroservice.constants.NaceEnum;
import com.nace.nacemicroservice.dto.AddedNaceDetailsDto;
import com.nace.nacemicroservice.dto.GetNaceDetailsDto;
import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import com.nace.nacemicroservice.exception.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Fozia
 */
@Component
public class ApiResponseBuilder {

    public ResponseEntity<AddedNaceDetailsDto> buildPostResponse(List<NaceDetailsEntity> result){
        AddedNaceDetailsDto returnDto = null;

        if(!Objects.isNull(result) && result.size() > 0){
            returnDto = AddedNaceDetailsDto.builder().status(NaceEnum.STATUS_SUCCESS.getValue())
                    .details(Arrays.asList(NaceEnum.ADD_RECORDS_SUCCESS_MSG.getValue()
                            .concat(StringUtils.defaultString(String.valueOf(result.size()),NaceEnum.ZERO.getValue())
                                    .concat(NaceEnum.RECORDS_MSG_TXT.getValue()))))
                    .timeStamp(LocalDateTime.now()).build();

        }else{
            returnDto = AddedNaceDetailsDto.builder().status(NaceEnum.STATUS_SUCCESS.getValue())
                    .details(Arrays.asList(NaceEnum.ADD_RECORDS_FAILURE_MSG.getValue()
                            .concat(StringUtils.defaultString(String.valueOf(result.size()),NaceEnum.ZERO.getValue())
                                    .concat(NaceEnum.RECORDS_MSG_TXT.getValue()))))
                    .timeStamp(LocalDateTime.now()).build();
        }
        return new ResponseEntity<AddedNaceDetailsDto>(returnDto, HttpStatus.OK);
    }

    public ResponseEntity<List<GetNaceDetailsDto>> buildGetResponse(List<NaceDetailsEntity> resultArg)
        throws EntityNotFoundException {

        List<GetNaceDetailsDto> returnDtoList = new ArrayList<GetNaceDetailsDto>();
        if(!Objects.isNull(resultArg) && resultArg.size() > 0) {
            resultArg.stream().forEach(result -> {
                returnDtoList.add(GetNaceDetailsDto.builder()
                        .order(result.getOrder())
                        .level(result.getLevel())
                        .code(result.getCode())
                        .parent(result.getParent())
                        .description(result.getDescription())
                        .itemIncludes(result.getItemIncludes())
                        .itemAlsoIncludes(result.getItemAlsoIncludes())
                        .rulings(result.getRulings())
                        .itemExcludes(result.getItemExcludes())
                                .referencesIsic(result.getReferenceIsic())
                                .build());
            });

        } else{
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<List<GetNaceDetailsDto>>(returnDtoList, HttpStatus.OK);
    }
}
