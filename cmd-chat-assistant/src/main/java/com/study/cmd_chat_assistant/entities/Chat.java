package com.study.cmd_chat_assistant.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "chat_memory")
@Entity
@Profile("devPG")
public class Chat {
        @Id
        @Column(name="id", nullable = false)
        UUID id;

        @Column(name="conversation_id", nullable = false)
        String conversationId;

        @Column(name="content", nullable = false, length = 9999)
        String content;

        @Column(name="created_at", nullable = false)
        LocalDateTime createdAt;
}
