package org.project2.omwp2.comment.reposistory;

import org.project2.omwp2.entity.BoardEntity;
import org.project2.omwp2.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByBoardEntityOrderByCommentIdDesc(BoardEntity boardEntity);

}
