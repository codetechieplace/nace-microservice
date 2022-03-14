package com.nace.nacemicroservice.config;

import com.nace.nacemicroservice.constants.NaceDetailsConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * EhCache Configuration.
 *
 * @author Fozia
 */
@Configuration
public class EhCacheConfig {

    @Bean
    public CacheManager cacheManager(){
        return CacheManager.create();
    }

    public EhCacheCacheManager naceEhCacheConfigManager(){

        Cache existingCache = cacheManager().getCache(NaceDetailsConstants.NACE_CACHE_NAME);

        if (Objects.isNull(existingCache)){
            CacheConfiguration ehCacheConfig = new CacheConfiguration().eternal(false).maxEntriesLocalHeap(0)
                    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU).name(NaceDetailsConstants.NACE_CACHE_NAME)
                    .timeToLiveSeconds(Long.valueOf(86400));

            Cache cache = new Cache(ehCacheConfig);
            cacheManager().addCache(cache);
        }
        return new EhCacheCacheManager(cacheManager());
    }
}
