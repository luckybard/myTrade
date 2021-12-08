package com.myTrade.repositories;

import com.myTrade.entities.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query(value = "SELECT message.* FROM message INNER JOIN conversation_message_list cml on message.id = cml.message_list_id " +
            "AND conversation_entity_id = :conversationId", nativeQuery = true)
    List<MessageEntity> findMessageEntityListByConversationId(@Param("conversationId") Long conversationId);
}
