package com.study.cmd_chat_assistant.repos;

import com.study.cmd_chat_assistant.entities.Chat;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("devPG")
public interface ChatMemoryRepository extends JpaRepository<Chat, String> {

    @Query("SELECT c FROM Chat c WHERE c.conversationId = :conversationId ORDER BY c.createdAt DESC")
    List<Chat> findChatByConversationId(String conversationId, Pageable pageable);

    List<Chat> findChatByConversationId(String conversationId);

    void deleteByConversationId(String conversationId);
}
