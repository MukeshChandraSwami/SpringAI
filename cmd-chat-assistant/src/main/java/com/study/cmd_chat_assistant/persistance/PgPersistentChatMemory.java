package com.study.cmd_chat_assistant.persistance;

import com.study.cmd_chat_assistant.entities.Chat;
import com.study.cmd_chat_assistant.repos.ChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Profile("devPG")
public class PgPersistentChatMemory implements ChatMemory {

    @Autowired
    private ChatMemoryRepository chatMemoRepo;

    @Override
    public void add(String conversationId, Message message) {
        this.add(conversationId, List.of(message));
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<Chat> chats = messageToChat(conversationId, messages);
        chatMemoRepo.saveAll(chats);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return chatMemoRepo.findChatByConversationId(conversationId, Pageable.ofSize(lastN))
                .stream()
                .map(chat -> (Message) new UserMessage(chat.getContent()))
                .toList();
    }

    @Override
    public void clear(String conversationId) {
        chatMemoRepo.deleteByConversationId(conversationId);
    }

    private List<Chat> messageToChat(String conversationId, List<Message> messages) {
        return messages.stream()
                .map(message -> Chat.builder()
                        .content(message.getContent())
                        .conversationId(conversationId)
                        .id(UUID.randomUUID())
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();
    }
}
