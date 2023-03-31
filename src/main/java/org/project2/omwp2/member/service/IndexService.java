package org.project2.omwp2.member.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.reposistory.BoardReposistory;
import org.project2.omwp2.dto.BoardDto;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.dto.NoticeDto;
import org.project2.omwp2.entity.BoardEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.entity.NoticeEntity;
import org.project2.omwp2.entity.ProfileEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.project2.omwp2.member.repository.NoticeRepository2;
import org.project2.omwp2.member.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final BoardReposistory boardReposistory;
    private final NoticeRepository2 noticeRepository2;

    //    index 페이지 내 일반게시판 get
    public List<BoardDto> getBoardList() {

        List<BoardDto> boardDtoList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardReposistory.findAll(Sort.by(Sort.Direction.DESC,"boardCreate"));

        if(boardEntityList.isEmpty()){
//            해당 게시글이 없으면 빈 리스트만 반환
            return boardDtoList;
        }

//        5개까지만 담음
        for(BoardEntity boardEntity : boardEntityList){
            boardDtoList.add(BoardDto.toBoardDto(boardEntity));
            if(boardDtoList.size()==5) {
//               길이가 5가 되면 반복문 종료
                return boardDtoList;
            }
        }

        return boardDtoList;

    }

    //    index 페이지 내 공지사항 get
    public List<NoticeDto> getNoticeList() {

        List<NoticeDto> noticeDtoList = new ArrayList<>();
        List<NoticeEntity> noticeEntityList = noticeRepository2.findAll(Sort.by(Sort.Direction.DESC,"noticeCreate"));
        
        if(noticeEntityList.isEmpty()){
//           해당 게시글이 없으면 빈 리스트만 반환
            return noticeDtoList;
        }

        for (NoticeEntity noticeEntity : noticeEntityList){
            noticeDtoList.add(NoticeDto.toNoticeDto(noticeEntity));

            if(noticeDtoList.size()==5) {
//               길이가 5가 되면 반복문 종료
                return noticeDtoList;
            }
        }

            return noticeDtoList;
    }
}
