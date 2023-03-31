package org.project2.omwp2.chatbot.repository;

import org.project2.omwp2.entity.ChatDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<ChatDepartmentEntity,Long > {
  Optional<ChatDepartmentEntity> findByDname(String dname);

  List<ChatDepartmentEntity> findAllByDepth(int depth);

  Optional<ChatDepartmentEntity> findAllByDepthAndDname(int i, String dname);
}
