package com.nace.nacemicroservice.services;

import com.nace.nacemicroservice.constants.NaceDetailsConstants;
import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import com.nace.nacemicroservice.filters.RequestCorrelation;
import com.nace.nacemicroservice.repository.NaceDetailsRepository;
import com.nace.nacemicroservice.services.executors.AddNaceDetailsExecutor;
import com.nace.nacemicroservice.utils.CsvFileReader;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Fozia
 */
@Service
public class NaceDetailsService {
    private static final Logger LOG= LoggerFactory.getLogger(NaceDetailsService.class);

    @Autowired
    private NaceDetailsRepository naceDetailsRepository;

    @Autowired
    CsvFileReader csvFileReader;

    @Autowired
    AddNaceDetailsExecutor addNaceDetailsExecutor;

    public List<NaceDetailsEntity> putNaceDetails(String filePath)
            throws NumberFormatException, IOException, ConstraintViolationException,InterruptedException {
        List<NaceDetailsEntity> result = new ArrayList<>();

        String corrId = RequestCorrelation.getId();
        LOG.info("[{}] NaceDetailsService | Import CSV | Start", corrId);

        if (StringUtils.isEmpty(filePath)) {
            throw new ConstraintViolationException("Missing request header named 'file-path'.", null);
        }

        /** Convert CSV file to Java Object **/
        List<NaceDetailsEntity> naceDetails = csvFileReader.readCVFile(filePath);

        /** Persist Java Object to database **/
        result = addNaceDetailsExecutor.execute(naceDetails, naceDetailsRepository);

        LOG.info("[{}] NaceDetailsService | Import CSV | Start", corrId);
        return result;
    }
        @Cacheable(cacheNames = NaceDetailsConstants.NACE_CACHE_NAME, key = "#order")
        public List<NaceDetailsEntity> getNaceDetailsByOrder(Long order){
            LOG.info("No entry found in cache, fetching it from the database for order: {}", order);
            return naceDetailsRepository.findByOrder(order);
        }

    }

