package org.project2.omwp2.account.repository;

import org.project2.omwp2.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    /* 현재 잔여금 */
    @Query(value = "SELECT SUM(ac_income) - SUM(ac_expend) FROM account WHERE ac_id <=:ac_id", nativeQuery = true)
    int findByAcId(@Param("ac_id") Long acId);

    /* 제목으로 검색 */
    Page<AccountEntity> findByAcTitleContaining(String search, Pageable pageable);

    /* 내용으로 검색 */
    Page<AccountEntity> findByAcContentContaining(String search, Pageable pageable);

    /* 제목, 내용*/
/*    @Query(value = "SELECT * FROM account WHERE ac_content like %:search% OR ac_title like %:search%", nativeQuery = true)
    Page<AccountEntity> findByAcTitleAndAcContent(@Param("search") String search, Pageable pageable);*/
}


