package org.project2.omwp2.board.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.reposistory.BoardReposistory;
import org.project2.omwp2.boardFile.reposistory.BoardFileReposistory;
import org.project2.omwp2.dto.BoardDto;
import org.project2.omwp2.entity.BoardEntity;
import org.project2.omwp2.entity.BoardFileEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardReposistory boardReposistory;
    private final BoardFileReposistory boardFileReposistory;

    @Transactional
    public void insertBoard(BoardDto boardDto, String mEmail) throws IOException {

        Optional<MemberEntity> optionalMemberEntity1
                =memberRepository.findBymEmail(mEmail);
        MemberEntity memberEntity = optionalMemberEntity1.get();

            if (boardDto.getBoardFile().isEmpty()) {


                BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDto,memberEntity);
                boardReposistory.save(boardEntity);
            } else {

                MultipartFile multipartFile = boardDto.getBoardFile();
                String originalName = multipartFile.getOriginalFilename();
                UUID uuid = UUID.randomUUID(); // 랜덤 이미지 이름

                String newName = uuid + "_" + originalName; //새로운 파일 이름
                String filePath = "C:/saveFiles/" + newName; // 파일 경로 탐색

                multipartFile.transferTo(new File(filePath));

                BoardEntity boardEntity = BoardEntity.toBoardEntityFileInclude(boardDto,memberEntity);
                Long boardId = boardReposistory.save(boardEntity).getBoardId();

                Optional<BoardEntity> boardEntity1 = boardReposistory.findById(boardId);
                BoardEntity boardEntity2 = boardEntity1.get(); //id에 해당하는 상품

                BoardFileEntity boardFileEntity = BoardFileEntity.toFileEntity(boardEntity2, originalName, newName);
                boardFileReposistory.save(boardFileEntity);


            }


    }
    
    //게시판 상세페이지 조회


    public BoardDto findByBoard(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = boardReposistory.findById(boardId);


        if (optionalBoardEntity.isPresent()){
            return BoardDto.toBoardDto(optionalBoardEntity.get());
        }else {
            return null;
        }
    }
    
    //게시판 검색 (게시글번호 검색)
    public Page<BoardDto> optionboardIdSearchPaging(Long boardId, Pageable pageable) {


        Page<BoardEntity> boardEntityPage = boardReposistory.findByBoardId(boardId,pageable);
        Page<BoardDto> boardDtoPage = boardEntityPage.map(boardEntity -> BoardDto.toBoardDto(boardEntity));


        return  boardDtoPage;
    }


    public Page<BoardDto> optionboardTitleSearchPaging(String search, Pageable pageable) {
        Page<BoardEntity> boardEntityPage = boardReposistory.findByBoardTitleContaining(search, pageable);
        Page<BoardDto> boardDtoPage = boardEntityPage.map(boardEntity -> BoardDto.toBoardDto(boardEntity));

        return boardDtoPage;
    }

    public Page<BoardDto> optionboardContentSearchPaging(String search, Pageable pageable) {
        Page<BoardEntity> boardEntityPage = boardReposistory.findByBoardContentContaining(search, pageable);
        Page<BoardDto> boardDtoPage = boardEntityPage.map(boardEntity -> BoardDto.toBoardDto(boardEntity));

        return boardDtoPage;

    }

    //게시판 전체

    public Page<BoardDto> BoardAllPagingList(Pageable pageable) {

        Page<BoardEntity> boardEntityPage = boardReposistory.findAll(pageable);
        Page<BoardDto> boardDtoPage = boardEntityPage.map(boardEntity -> BoardDto.toBoardDto(boardEntity));

        return boardDtoPage;
    }

    @Transactional
    public void upHit(Long boardId) {
        boardReposistory.upHitGo(boardId);
    }

    @Transactional
    public void UpCommentCount(Long boardId) {
        boardReposistory.upCmcountCount1(boardId);
    }

    @Transactional
    public void boardUpdateDo(BoardDto boardDto, String mEmail) {
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findBymEmail(mEmail);

        MemberEntity memberEntity = optionalMemberEntity.get();

        if (boardDto.getBfileOldName().isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toBoardUpdateEntity(boardDto, memberEntity);
            boardReposistory.save(boardEntity);
        }else {
            BoardEntity boardEntity = BoardEntity.toBoardUpdateEntity2(boardDto, memberEntity);
            boardReposistory.save(boardEntity);

        }

    }

    @Transactional
    public void boardDeleteDo(Long productId) {
        boardReposistory.deleteById(productId);
    }

    public Page<BoardDto> myBoardListDo(Long mId, Pageable pageable) {

        Page<BoardEntity> boardEntityPage = boardReposistory.findAllBymId(mId,pageable);
        Page<BoardDto> boardDtoPage = boardEntityPage.map(boardEntity -> BoardDto.toBoardDto(boardEntity));

        return boardDtoPage;
    }


    @Transactional
    public void downCommentCount(Long boardId) {
        boardReposistory.downCmcount(boardId);
    }
}
