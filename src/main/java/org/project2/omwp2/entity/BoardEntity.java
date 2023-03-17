package org.project2.omwp2.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class BoardEntity {
// 커뮤니티 게시판 (파일첨부, 댓글 가능)
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;
//    등록일
    @CreationTimestamp
    @Column(name = "board_create", updatable = false)
    private LocalDateTime boardCreate;
//    수정일
    @UpdateTimestamp
    @Column(name = "board_update", insertable = false)
    private LocalDateTime boardUpdate;
//    제목
    @Column(name = "board_title",nullable = false)
    private String boardTitle;
//    내용
    @Column(name = "board_content", nullable = false)
    private String boardContent;
//    조회수
    @Column(name = "board_hit")
    private int boardHit;
//    댓글수
    @Column(name = "board_cmcount")
    private int boardCmcount;

    // 파일 유무
    @Column(name = "board_attach")
    private int boardAttach;

//    작성자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;

//   첨부파일
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardFileEntity> boardFileEntities = new ArrayList<>();
    
//    댓글
    @OneToMany(mappedBy = "boardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<CommentEntity> commentEntities = new ArrayList<>();

}
