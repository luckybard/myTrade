package com.myTrade.repositories;

import com.myTrade.entities.ConversationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity,Long> {

    @Query(value = "SELECT * FROM conversation LEFT JOIN message ON conversation.id = message.id where conversation.id= :conversationId", nativeQuery = true)
    List<ConversationEntity> findConversationMessageEntityListByConversationId(@Param("conversationId")Long conversationId);

    @Query(value ="SELECT * FROM conversation WHERE conversation.sender_username = :username OR conversation.recipient_username =:username", nativeQuery = true)
    Page<ConversationEntity> findByRecipientUsernameOrSenderUsername(String username, Pageable pageable);


}
