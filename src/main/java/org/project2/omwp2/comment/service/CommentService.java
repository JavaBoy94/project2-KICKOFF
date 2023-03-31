package org.project2.omwp2.comment.service;


import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.reposistory.BoardReposistory;
import org.project2.omwp2.comment.reposistory.CommentRepository;
import org.project2.omwp2.dto.CommentDto;
import org.project2.omwp2.entity.BoardEntity;
import org.project2.omwp2.entity.CommentEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final BoardReposistory boardReposistory;
    private final CommentRepository commentRepository;

    @Transactional
    public Long insertCommentDo(CommentDto commentDto,
                                String mEmail
                                ) {
        // member테이블의 이메일이 있는지 확인
        Optional<MemberEntity> optionalMemberEntity
        = memberRepository.findBymEmail(mEmail);

        // product 테이블의 상품번호가 있는지 확인
        Optional<BoardEntity> optionalBoardEntity
                = boardReposistory.findById(commentDto.getBoardId());
        if (optionalBoardEntity.isPresent()
            &&optionalMemberEntity.isPresent()
            ){

            MemberEntity memberEntity = optionalMemberEntity.get();
             BoardEntity boardEntity = optionalBoardEntity.get();

            CommentEntity commentEntity =
                    CommentEntity.toInsertEntity(commentDto, memberEntity, boardEntity);

            return commentRepository.save(commentEntity).getCommentId();

        }else {
            return null;
        }
    }

    public List<CommentDto> commentDtoListDo(Long boardId) {

        BoardEntity boardEntity = boardReposistory.findById(boardId).get();

        List<CommentEntity> commentEntityList =
                commentRepository.findAllByBoardEntityOrderByCommentIdDesc(boardEntity);

        List<CommentDto> commentDtoList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList){
            CommentDto commentDto = CommentDto.toCommentDto(commentEntity, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
    public int commetDeleteDo(Long commentId, String email) {
        //현재로그인 한 회원 아이디를 가져온다.
        Long mId = memberRepository.findBymEmail(email).get().getMId();
        //댓글 작성자의 정보를 가져온다.
        Long mId2 = commentRepository.findById(commentId).get().getMemberEntity().getMId();
        if (mId == mId2) {
            //로그인 한 아이디랑 해당 댓글 작성자의 아이디가 같으면 댓글 삭제 실행
            commentRepository.deleteById(commentId);
        }
        if (commentRepository.findById(commentId).isEmpty()) {
            return 1;
        }
        return 0;
    }

    public Long findBoardId(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        Long boarId = commentEntity.getBoardEntity().getBoardId();

        return boarId;

    }

    public CommentDto findByCommet(Long commentId) {

        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);

        if (optionalCommentEntity.isPresent()){
            return CommentDto.toCommentUpdateDo(optionalCommentEntity.get());
        }
        return null;
    }

    @Transactional
    public void commentUpdateDo(CommentDto commentDto, String mEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findBymEmail(mEmail);
        MemberEntity memberEntity = optionalMemberEntity.get();

        Optional<BoardEntity> optionalBoardEntity = boardReposistory.findById(commentDto.getBoardId());
        BoardEntity boardEntity = optionalBoardEntity.get();

        CommentEntity commentEntity = CommentEntity.toUpdateEntity(commentDto,memberEntity,boardEntity);
        commentRepository.save(commentEntity);

    }
}
