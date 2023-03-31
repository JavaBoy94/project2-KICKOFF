package org.project2.omwp2.board.reposistory;

import org.project2.omwp2.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReposistory extends JpaRepository<BoardEntity, Long> {

    Page<BoardEntity> findByBoardId(Long boardId, Pageable pageable);

    Page<BoardEntity> findByBoardTitleContaining(String search, Pageable pageable);

    Page<BoardEntity> findByBoardContentContaining(String search, Pageable pageable);


//    @Query(value = "SELECT count(*) from board b inner join comment r " +
//            "on b.board_id = r.board_id " +
//            "where b.board_id=:id ;", nativeQuery = true)
//    int Count(@Param("id") Long boardId);


    @Modifying
    @Query(value = " update BoardEntity b set b.boardHit=b.boardHit+1 where b.boardId=:boardId " )
    void upHitGo(Long boardId);


    @Modifying
    @Query(value = "update BoardEntity b set b.boardCmcount=b.boardCmcount+1  " +
            "where b.boardId=:boardId ")
    void upCmcountCount1(@Param("boardId") Long boardId);


    @Query(value = "select * from board " +
            "where m_id=:mId",nativeQuery = true)
    Page<BoardEntity> findAllBymId(@Param("mId") Long mId, Pageable pageable);

    @Modifying
    @Query(value = "update BoardEntity b set b.boardCmcount=b.boardCmcount-1 " +
            "where b.boardId=:boardId ")
    void downCmcount(@Param("boardId") Long boardId);
}
