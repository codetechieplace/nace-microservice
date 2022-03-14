package com.nace.nacemicroservice.utils;


import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import com.nace.nacemicroservice.filters.RequestCorrelation;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Fozia
 */
@Component
public class CsvFileReader {

    private static final Logger LOG = LoggerFactory.getLogger(CsvFileReader.class);

    public List<NaceDetailsEntity> readCVFile(String filePath) {
        List<NaceDetailsEntity> naceDetails = new ArrayList<>();

        String corrId = RequestCorrelation.getId();
        LOG.info("CsvFileReader Starts", corrId);
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] record;
            int row = 0;
            while ((record = reader.readNext()) != null) {

                if (row > 0) {
                    NaceDetailsEntity nace = buildNaceExcelDetails(record);
                    if (!Objects.isNull(nace)) {
                        naceDetails.add(nace);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("Exception occured", corrId);
        }
        LOG.info("CsvFileReader Ends", corrId);
        return naceDetails;
    }
        private NaceDetailsEntity buildNaceExcelDetails (String[]record){
        NaceDetailsEntity nace = NaceDetailsEntity.builder()
                .order(Long.valueOf(getDefaultValue(record[0])))
                .level(Long.valueOf(getDefaultValue(record[1])))
                .code(getDefaultValue(record[2]))
                .parent(getDefaultValue(record[3]))
                .description(getDefaultValue(record[4]))
                .itemIncludes(getDefaultValue(record[5]))
                .itemAlsoIncludes(getDefaultValue(record[6]))
                .rulings(getDefaultValue(record[7]))
                .itemExcludes(getDefaultValue(record[8]))
                .referenceIsic(getDefaultValue(record[9]))
                .build();
        return nace;
        }

    private String getDefaultValue(String record) {
        return StringUtils.defaultString(record,StringUtils.EMPTY);
    }

}

