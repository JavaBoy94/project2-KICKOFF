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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final BoardReposistory boardReposistory;
    private final CommentRepository commentRepository;

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
}
