package org.project2.omwp2.member.repository;

import org.project2.omwp2.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository2 extends JpaRepository<NoticeEntity,Long> {
}
