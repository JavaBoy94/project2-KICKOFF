package org.project2.omwp2.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.dto.BoardDto;

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
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int boardHit;
//    댓글수
    @Column(name = "board_cmcount", nullable = true, columnDefinition = "int default 0")
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

    //파일 없을 때
    public static BoardEntity toBoardEntity(BoardDto boardDto, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        System.out.println(boardDto.getBoardTitle()+" <<");

        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardAttach(0); // 파일 여부
        boardEntity.setBoardHit(boardDto.getBoardHit());
        boardEntity.setBoardCmcount(boardDto.getBoardCmcount());
        boardEntity.setMemberEntity(memberEntity);

        System.out.println("111111");

        return boardEntity;

    }

    //파일 있을 때
    public  static BoardEntity toBoardEntityFileInclude(BoardDto boardDto, MemberEntity memberEntity){
        BoardEntity boardEntity = new BoardEntity();
        System.out.println(boardDto.getBoardTitle()+" <<");

        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardCmcount(boardDto.getBoardCmcount());
        boardEntity.setBoardHit(boardDto.getBoardHit());
        boardEntity.setBoardAttach(1);
        boardEntity.setMemberEntity(memberEntity);

        System.out.println("File include");

        return boardEntity;
    }

    public static BoardEntity toBoardUpdateEntity(BoardDto boardDto, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardId(boardDto.getBoardId());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardUpdate(boardDto.getBoardUpdate());
        boardEntity.setBoardHit(boardDto.getBoardHit());
        boardEntity.setBoardCmcount(boardDto.getBoardCmcount());
        boardEntity.setMemberEntity(memberEntity);

        boardEntity.setBoardAttach(0);

        return boardEntity;
    }

    public static BoardEntity toBoardUpdateEntity2(BoardDto boardDto, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardId(boardDto.getBoardId());
        boardEntity.setBoardTitle(boardDto.getBoardTitle());
        boardEntity.setBoardContent(boardDto.getBoardContent());
        boardEntity.setBoardHit(boardDto.getBoardHit());
        boardEntity.setBoardCmcount(boardDto.getBoardCmcount());
        boardEntity.setBoardUpdate(boardDto.getBoardUpdate());
        boardEntity.setMemberEntity(memberEntity);

        boardEntity.setBoardAttach(1);

        return boardEntity;

    }



}


