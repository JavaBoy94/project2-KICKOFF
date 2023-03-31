package org.project2.omwp2.member.repository;

import org.project2.omwp2.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findBymEmail(String mEmail);

    // 포지션별
    @Query(value = "SELECT * FROM member WHERE m_position =:position order by m_create desc",nativeQuery = true)
    List<MemberEntity> findBymPositionDesc(@Param("position") String mPosition);

    Page<MemberEntity> findAllBymPosition(String mPosition, Pageable pageable);

    // 회원구분별
    @Query(value = "SELECT * FROM member WHERE m_dept =:dept order by m_create desc",nativeQuery = true)
    List<MemberEntity> findBymDeptDesc(@Param("dept") String mDept);

    Page<MemberEntity> findAllBymDept(String mDept, Pageable pageable);

//    @Query(value = "select * from member where m_name=:search order by m_create desc",nativeQuery = true)
    Page<MemberEntity> findBymNameContaining(String search, Pageable pageable);

//    @Query(value = "select * from member where m_email like %:search%",nativeQuery = true)
    Page<MemberEntity> findBymEmailContaining(String search, Pageable pageable);

//    @Query(value = "select * from member where m_tel like %:search%",nativeQuery = true)
    Page<MemberEntity> findBymTelContaining(String search, Pageable pageable);
}
