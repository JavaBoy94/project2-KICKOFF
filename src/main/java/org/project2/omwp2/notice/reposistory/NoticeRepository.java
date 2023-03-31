package org.project2.omwp2.notice.reposistory;


import org.project2.omwp2.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {


    Page<NoticeEntity> findByNoticeId(Long noticeId, Pageable pageable);

//    Page<NoticeEntity> findByNoticeTitle(String search, Pageable pageable);

//    Page<NoticeEntity> findByNoticeContent(String search, Pageable pageable);

    Page<NoticeEntity> findByNoticeTitleContaining(String search, Pageable pageable);

    Page<NoticeEntity> findByNoticeContentContaining(String search, Pageable pageable);

    @Modifying
    @Query(value = "update NoticeEntity n set n.noticeHit=n.noticeHit+1 " +
            " where n.noticeId=:id ")
    void upHitGo(Long id);

}
