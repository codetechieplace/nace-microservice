package com.nace.nacemicroservice.repository;

import com.nace.nacemicroservice.entity.NaceDetailsEntity;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NaceDetailsRepository extends JpaRepository<NaceDetailsEntity, Long> {

    public List<NaceDetailsEntity> findByOrder(Long order);

    public void deleteByOrder(Long Order);
}
