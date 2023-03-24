package org.project2.omwp2.calendar.repository;

import org.project2.omwp2.entity.AttendEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendRepository extends JpaRepository<AttendEntity, Long> {


    @Query(value = "select * from attend where m_id=:id order by attend_id desc limit 1",nativeQuery = true)
    AttendEntity findByLastLine(@Param("id") Long id);

    @Query(value = "select * from attend where m_id=:id", nativeQuery = true)
    List<AttendEntity> findAllByMId(@Param("id")Long userId);

    @Query(value = "select * from attend where m_id=:id", nativeQuery = true)
    Page<AttendEntity> findByMMId(@Param("id") Long id, Pageable pageable);

}
