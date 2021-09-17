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

    List<AdEntity> findAdEntitiesByIsActiveTrue();

    @Query(value ="SELECT * FROM ad WHERE lower(ad_category) LIKE :category AND lower(city) LIKE :city AND lower(price) BETWEEN :price_from AND :price_to", nativeQuery = true)
    List<AdEntity> findAdEntitiesByAdRequest(@Param("category")String category,@Param("city")String city,@Param("price_from")int priceFrom,@Param("price_to")int priceTo);
}
