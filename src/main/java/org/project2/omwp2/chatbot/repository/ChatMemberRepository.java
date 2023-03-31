package org.project2.omwp2.chatbot.repository;

import org.project2.omwp2.entity.ChatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMemberRepository extends JpaRepository<ChatMemberEntity,Long > {

  Optional<ChatMemberEntity> findBycName(String cName);
}
