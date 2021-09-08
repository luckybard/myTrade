package com.myTrade.repositories;

import com.myTrade.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByUserName(String userName);  //add optional TODO:
    boolean existsByEmail(String email);

}
