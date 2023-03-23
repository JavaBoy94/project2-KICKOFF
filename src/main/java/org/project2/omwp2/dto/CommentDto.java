package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.entity.CommentEntity;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommentDto {

    private Long commentId;
    private LocalDateTime commentCreate;
    private LocalDateTime commentUpdate;
    private String commentContent;

    private String mEmail;

    private Long boardId;

    public static CommentDto toCommentDto(CommentEntity commentEntity,
                                          Long boardId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(commentEntity.getCommentId());
        commentDto.setCommentContent(commentEntity.getCommentContent());
        commentDto.setCommentCreate(commentEntity.getCommentCreate());
        commentDto.setMEmail(commentEntity.getMemberEntity().getMEmail());
        commentDto.setBoardId(boardId);
        System.out.println("다시?");

        return commentDto;

    }

}
