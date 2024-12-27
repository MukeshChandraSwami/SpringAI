package com.study.cmd_chat_assistant.advisors;

import com.study.cmd_chat_assistant.persistance.PgPersistentChatMemory;
import lombok.Setter;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Service
@Profile("devPG")
public class CustomPgChatMemoryAdvisor extends MessageChatMemoryAdvisor {

    private UUID conversationId;

    public CustomPgChatMemoryAdvisor(PgPersistentChatMemory chatMemory) {
        super(chatMemory);
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest.adviseContext().putIfAbsent(CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId);
        advisedRequest.adviseContext().putIfAbsent(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10);

        advisedRequest = this.before(advisedRequest);

        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

        this.after(advisedResponse);

        return advisedResponse;
    }

    @Override
    public String getName() {
        return "CustomChatMemoryAdvisor-PG";
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }


    private AdvisedRequest before(AdvisedRequest request) {

        String conversationId = this.doGetConversationId(request.adviseContext());

        int chatMemoryRetrieveSize = this.doGetChatMemoryRetrieveSize(request.adviseContext());

        // 1. Retrieve the chat memory for the current conversation.
        List<Message> memoryMessages = this.getChatMemoryStore().get(conversationId, chatMemoryRetrieveSize);

        // 2. Advise the request messages list.
        List<Message> advisedMessages = new ArrayList<>(request.messages());
        advisedMessages.addAll(memoryMessages);

        // 3. Create a new request with the advised messages.
        AdvisedRequest advisedRequest = AdvisedRequest.from(request).withMessages(advisedMessages).build();

        // 4. Add the new user input to the conversation memory.
        UserMessage userMessage = new UserMessage(request.userText(), request.media());
        this.getChatMemoryStore().add(this.doGetConversationId(request.adviseContext()), userMessage);

        return advisedRequest;
    }

    private void after(AdvisedResponse advisedResponse) {

        List<Message> assistantMessages = advisedResponse.response()
                .getResults()
                .stream()
                .map(g -> (Message) g.getOutput())
                .toList();

        this.getChatMemoryStore().add(this.doGetConversationId(advisedResponse.adviseContext()), assistantMessages);
    }
}
