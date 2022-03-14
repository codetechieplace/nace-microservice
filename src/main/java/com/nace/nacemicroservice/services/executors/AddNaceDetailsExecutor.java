package com.nace.nacemicroservice.services.executors;

import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import com.nace.nacemicroservice.repository.NaceDetailsRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Fozia
 */
@Component
public class AddNaceDetailsExecutor {
    private static final int PARTITION_SIZE = 500;

    public List<NaceDetailsEntity> execute(List<NaceDetailsEntity> naceDetails, NaceDetailsRepository naceDetailsRepository)
        throws InterruptedException {

        List<NaceDetailsEntity> result;
        final AtomicInteger counter = new AtomicInteger(0);
        Collection<List<NaceDetailsEntity>> partionedNaceDetails = naceDetails.stream()
                .collect(Collectors.groupingBy(s-> counter.getAndIncrement() /PARTITION_SIZE )).values();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Callable<List<NaceDetailsEntity>>> callables = new ArrayList<>();
        partionedNaceDetails.forEach(sublist-> {
            callables.add(new AddNaceDetailsTask(sublist,naceDetailsRepository));
        });

        Stream<List<NaceDetailsEntity>> map = executor.invokeAll(callables).stream().map(future -> {
            try{
                return future.get();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        result = map.flatMap(List::stream).collect(Collectors.toList());
        shutdownExecuterService(executor);
        return result;


    }

    private void shutdownExecuterService(ExecutorService executor) {
        executor.shutdown();
        try {
            if(!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)){
                executor.shutdown();
            }
        }catch (InterruptedException e){
            executor.shutdown();
        }
    }
}
