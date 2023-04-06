package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.BoardEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDto {

    private Long boardId;
    private LocalDateTime boardCreate;
    private LocalDateTime boardUpdate;
    private String boardTitle;
    private String boardContent;
    private int boardHit;
    private int boardCmcount;


    private Long memberId;

    private String mEmail;

    private String mName;
    //파일 관련
    private int boardAttach;

    private MultipartFile boardFile; //파일 저장객체

    private Long bfileId; // 파일 번호

    private String bfileOldName; //파일 오래된 이름

    private String bfileNewName; //파일 새로운 이름


    public static BoardDto toBoardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(boardEntity.getBoardId());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardContent(boardEntity.getBoardContent());
        boardDto.setBoardCreate(boardEntity.getBoardCreate());
        boardDto.setBoardUpdate(boardEntity.getBoardUpdate());
        boardDto.setBoardCmcount(boardEntity.getBoardCmcount());
        boardDto.setBoardHit(boardEntity.getBoardHit());
        boardDto.setMemberId(boardEntity.getMemberEntity().getMId());
        boardDto.setMEmail(boardEntity.getMemberEntity().getMEmail());
        boardDto.setMName(boardEntity.getMemberEntity().getMName());
        System.out.println("작성자"+boardDto.getMemberId());


        if (boardEntity.getBoardAttach() == 0) {
            boardDto.setBoardAttach(boardEntity.getBoardAttach());
        } else {
            boardDto.setBoardAttach(boardEntity.getBoardAttach());

//            boardDto.setBfileId(boardEntity.getBoardFileEntities().get(0).getBfileId());
            System.out.println("뭐지?");
            boardDto.setBfileOldName(boardEntity.getBoardFileEntities().get(0).getBfileOldName());
            boardDto.setBfileNewName(boardEntity.getBoardFileEntities().get(0).getBfileNewName());
        }

        return boardDto;
    }
}
