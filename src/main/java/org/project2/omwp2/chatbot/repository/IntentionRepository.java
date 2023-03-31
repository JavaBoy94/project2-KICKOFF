package org.project2.omwp2.chatbot.repository;

import org.project2.omwp2.entity.ChatIntentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface IntentionRepository extends JpaRepository<ChatIntentionEntity,Long > {
  Optional<ChatIntentionEntity> findByName(String token);

  Optional<ChatIntentionEntity> findByNameAndUpper(String token, ChatIntentionEntity upper);
}
