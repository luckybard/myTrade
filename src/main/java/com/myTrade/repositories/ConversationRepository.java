package com.myTrade.repositories;

import com.myTrade.entities.ConversationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {

    @Query(value = "SELECT * FROM conversation WHERE conversation.sender_username = :username OR conversation.recipient_username =:username", nativeQuery = true)
    Page<ConversationEntity> findConversationEntityPageByRecipientOrSenderUsername(String username, Pageable pageable);
}
