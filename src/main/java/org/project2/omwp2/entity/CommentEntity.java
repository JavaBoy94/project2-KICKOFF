package org.project2.omwp2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.dto.CommentDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class CommentEntity {
    
//    커뮤니티 게시판 댓글

    //댓글 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    //등록일
    @CreationTimestamp
    @Column(name = "comment_create", updatable = false)
    private LocalDateTime commentCreate;

    //수정일
    @UpdateTimestamp
    @Column(name = "comment_update", insertable = false)
    private LocalDateTime commentUpdate;

    //내용
    @Column(name = "comment_content", nullable = false)
    private String commentContent;

//    게시글 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

//    댓글작성자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;

    public static CommentEntity toInsertEntity(CommentDto commentDto, MemberEntity memberEntity , BoardEntity boardEntity) {

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentContent(commentDto.getCommentContent());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);

        return commentEntity;
    }

    public static CommentEntity toUpdateEntity(CommentDto commentDto, MemberEntity memberEntity, BoardEntity boardEntity) {

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentId(commentDto.getCommentId());
        commentEntity.setCommentContent(commentDto.getCommentContent());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);

        return  commentEntity;


    }

}
