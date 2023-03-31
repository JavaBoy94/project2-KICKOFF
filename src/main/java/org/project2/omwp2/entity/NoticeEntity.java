package org.project2.omwp2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.dto.NoticeDto;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
public class NoticeEntity {
    
//    공지게시판 (파일첨부, 댓글 불가능) >> 관리자만 작성,수정,삭제 가능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    //등록일
    @CreationTimestamp
    @Column(name = "notice_create", updatable = false)
    private LocalDateTime noticeCreate;

    //수정일
    @UpdateTimestamp
    @Column(name = "notice_update", insertable = false)
    private LocalDateTime noticeUpdate;

    //공지사항 제목
    @Column(name = "notice_title", nullable = false)
    private String noticeTitle;

    //공지사항 내용
    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    //공지사항 조회 수
    @Column(name = "notice_hit", columnDefinition = "integer default 0")
    private int noticeHit;

    //    작성자 ID >> 관리자만
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;

    public static NoticeEntity toNoticeInsertEntity(NoticeDto noticeDto, MemberEntity memberEntity) {
        NoticeEntity noticeEntity = new NoticeEntity();

        noticeEntity.setNoticeTitle(noticeDto.getNoticeTitle());
        noticeEntity.setNoticeContent(noticeDto.getNoticeContent());
        noticeEntity.setMemberEntity(memberEntity);

        return noticeEntity;
    }

    public static NoticeEntity toNoticeUpdateEntity(NoticeDto noticeDto, MemberEntity memberEntity) {
        NoticeEntity noticeEntity = new NoticeEntity();

        noticeEntity.setNoticeId(noticeDto.getNoticeId());
        noticeEntity.setNoticeTitle(noticeDto.getNoticeTitle());
        noticeEntity.setNoticeContent(noticeDto.getNoticeContent());
        noticeEntity.setNoticeUpdate(noticeDto.getNoticeUpdate());
        noticeEntity.setNoticeHit(noticeDto.getNoticeHit());
        noticeEntity.setMemberEntity(memberEntity);

        return noticeEntity;
    }

}
