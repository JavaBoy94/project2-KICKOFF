package org.project2.omwp2.document.repository;

import org.project2.omwp2.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
