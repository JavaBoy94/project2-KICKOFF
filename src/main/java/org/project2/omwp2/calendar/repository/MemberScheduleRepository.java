package org.project2.omwp2.calendar.repository;

import org.project2.omwp2.entity.MemberScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberScheduleRepository extends JpaRepository<MemberScheduleEntity, Long> {

    @Query(value = "select * from member_schedule where m_id=:id", nativeQuery = true)
    List<MemberScheduleEntity> findAllByMId(@Param("id") Long id);

    @Query(value = "select * from member_schedule where content=:content",nativeQuery = true)
    Optional<MemberScheduleEntity> findByContentId(String content);

    @Query(value = "select * from member_schedule where content=:content",nativeQuery = true)
    List<MemberScheduleEntity> findByContentCount(String content);


    // 관리자 일정 조회
//    Page<MemberScheduleEntity> findByScheduleBoardContainingOrScheduleIdContaining(Pageable pageable, String search, Long id);



}
