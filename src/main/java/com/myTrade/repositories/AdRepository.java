package com.myTrade.repositories;

import com.myTrade.entities.AdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Long> {

    @Query(value = "SELECT * FROM ad WHERE is_active = TRUE AND ad_category LIKE :category AND city LIKE :city " +
            "AND price BETWEEN :price_from AND :price_to AND (lower(title) LIKE :word1 OR lower(title) LIKE :word2 OR " +
            "lower(title) LIKE :word3 OR lower(title) LIKE :word4 OR lower(title) LIKE :word5 OR lower(title) LIKE :word6 OR lower(title) LIKE :word7 OR " +
            "lower(title) LIKE :word8 OR lower(title) LIKE :word9 OR lower(title) LIKE :word10 OR lower(description) LIKE :word1 OR " +
            "lower(description) LIKE :word2 OR lower(description) LIKE :word3 OR lower(description) LIKE :word4 OR lower(description) LIKE :word5 OR " +
            "lower(description) LIKE :word6 OR lower(description) LIKE :word7 OR lower(description) LIKE :word8 OR lower(description) LIKE :word9 OR " +
            "lower(description) LIKE :word10)",nativeQuery = true)
    Page<AdEntity> findBySearchRequestUpgraded(@Param("category") String category, @Param("city") String city,
                                             @Param("price_from") int priceFrom, @Param("price_to") int priceTo,
                                             @Param("word1") String word1, @Param("word2") String word2,
                                             @Param("word3") String word3, @Param("word4") String word4,
                                             @Param("word5") String word5, @Param("word6") String word6,
                                             @Param("word7") String word7, @Param("word8") String word8,
                                             @Param("word9") String word9, @Param("word10") String word10,
                                             Pageable pageable);

    @Query(value = "SELECT * FROM ad WHERE is_active = TRUE AND ad_category LIKE :category AND city LIKE :city " +
            "AND price BETWEEN :price_from AND :price_to AND (lower(title) LIKE :title OR lower(description) LIKE :title)", nativeQuery = true)
    Page<AdEntity> findBySearchRequest(@Param("category") String category, @Param("city") String city,
                                       @Param("price_from") int priceFrom, @Param("price_to") int priceTo,
                                       @Param("title") String title, Pageable pageable);



    @Query(value = "SELECT * FROM ad WHERE is_active = TRUE AND ad_category LIKE :category AND city LIKE :city " +
            "AND price BETWEEN :price_from AND :price_to AND (lower(title) LIKE :title)", nativeQuery = true)
    Page<AdEntity> findBySearchRequestWithoutDescription(@Param("category") String category, @Param("city") String city,
                                                         @Param("price_from") int priceFrom, @Param("price_to") int priceTo,
                                                         @Param("title") String title, Pageable pageable);
}
