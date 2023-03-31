package org.project2.omwp2.chatbot.repository;

import org.project2.omwp2.entity.ChatAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AnswerRepository extends JpaRepository<ChatAnswerEntity,Long > {

}
