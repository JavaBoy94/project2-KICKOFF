package org.project2.omwp2.member.repository;

import org.project2.omwp2.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    @Query(value = "select * from profile p " +
            "where m_id=:mId",nativeQuery = true)
    ProfileEntity findAllByMId(@Param("mId") Long mId);
}
