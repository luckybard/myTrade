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
            "AND price BETWEEN :price_from AND :price_to AND (lower(title) LIKE :title OR lower(description) LIKE :title)", nativeQuery = true)
    Page<AdEntity> findActiveAdEntityPageBySearchRequest(@Param("category") String category, @Param("city") String city,
                                                         @Param("price_from") int priceFrom, @Param("price_to") int priceTo,
                                                         @Param("title") String title, Pageable pageable);

    @Query(value = "SELECT * FROM ad WHERE is_active = TRUE AND ad_category LIKE :category AND city LIKE :city " +
            "AND price BETWEEN :price_from AND :price_to AND (lower(title) LIKE :title)", nativeQuery = true)
    Page<AdEntity> findActiveAdEntityPageBySearchRequestWithoutDescription(@Param("category") String category, @Param("city") String city,
                                                                           @Param("price_from") int priceFrom, @Param("price_to") int priceTo,
                                                                           @Param("title") String title, Pageable pageable);

    @Query(value = "SELECT * FROM ad WHERE owner_username = :ownerUsername", nativeQuery = true)
    Page<AdEntity> findAdEntityPageByOwnerUsername(@Param("ownerUsername") String ownerUsername, Pageable pageable);

    @Query(value = "SELECT * FROM ad WHERE is_active = TRUE AND owner_username = :ownerUsername", nativeQuery = true)
    Page<AdEntity> findActiveAdEntityPageByOwnerUsername(@Param("ownerUsername") String ownerUsername, Pageable pageable);

    @Query(value = "SELECT ad.* FROM user_favourite_ad_entity_list, ad WHERE ad.id = user_favourite_ad_entity_list.favourite_ad_entity_list_id " +
            "AND user_entity_id = :userId ", nativeQuery = true)
    Page<AdEntity> findUserFavouriteAdEntityPageByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM ad WHERE is_active = TRUE", nativeQuery = true)
    Page<AdEntity> findActiveRandomAdEntityPage(Pageable pageable);
}
