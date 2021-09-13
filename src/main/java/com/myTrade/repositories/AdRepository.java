package com.myTrade.repositories;

import com.myTrade.entities.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AdRepository extends JpaRepository<AdEntity,Long> {

    @Query(value = "SELECT * FROM ad WHERE lower(title) LIKE :word OR lower(description) LIKE :word", nativeQuery = true)
    Set<AdEntity> findAdEntitiesByProvidedValue(@Param("word")String word);

    List<AdEntity> findAdEntitiesByActiveTrue();
}
