package com.nace.nacemicroservice.services.executors;

import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import com.nace.nacemicroservice.filters.RequestCorrelation;
import com.nace.nacemicroservice.repository.NaceDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Callable task to persist given list of Nace records to database.
 *
 * @author Fozia
 */
public class AddNaceDetailsTask implements Callable<List<NaceDetailsEntity>> {

    private static final Logger LOG = LoggerFactory.getLogger(AddNaceDetailsTask.class);

    private List<NaceDetailsEntity> naceDetails;
    private NaceDetailsRepository naceDetailsRepository;

    public AddNaceDetailsTask(List<NaceDetailsEntity> naceDetails,NaceDetailsRepository naceDetailsRepository){
        this.naceDetails = naceDetails;
        this.naceDetailsRepository = naceDetailsRepository;
    }


    @Override
    public List<NaceDetailsEntity> call() throws Exception {

        String corrId = RequestCorrelation.getId();
        LOG.info("[{}] AddNaceDetailsTask | call() | Start", corrId);
        List<NaceDetailsEntity> persistedRecords = this.naceDetailsRepository.saveAll(naceDetails);
        LOG.info("[{}] AddNaceDetailsTask | call() | Records Count: {} | Exit", corrId, persistedRecords.size());
        return persistedRecords;
    }
}
