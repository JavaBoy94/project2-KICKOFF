package org.project2.omwp2.approval.repository;

import org.project2.omwp2.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {

    Optional<ApprovalEntity> findById(Long id);
}
