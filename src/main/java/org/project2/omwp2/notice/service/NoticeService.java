package org.project2.omwp2.notice.service;


import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.NoticeDto;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.entity.NoticeEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.project2.omwp2.notice.reposistory.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    public Page<NoticeDto> optionNoticeIDSearch(Long noticeId, Pageable pageable) {

        Page<NoticeEntity> noticeEntityPage = noticeRepository.findByNoticeId(noticeId,pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }

    public Page<NoticeDto> optionNoticeTitleSearch(String search, Pageable pageable) {

        Page<NoticeEntity> noticeEntityPage = noticeRepository.findByNoticeTitleContaining(search,pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }

    public Page<NoticeDto> optionNoticeContentSearch(String search, Pageable pageable) {

        Page<NoticeEntity> noticeEntityPage = noticeRepository.findByNoticeContentContaining(search,pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }

    public Page<NoticeDto> noticeAll(Pageable pageable) {

        Page<NoticeEntity> noticeEntityPage = noticeRepository.findAll(pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }


    @Transactional
    public void InsertNoticeDo(NoticeDto noticeDto, String mEmail) {

        Optional<MemberEntity> optionalMemberEntity
                = memberRepository.findBymEmail(mEmail);

        MemberEntity memberEntity = optionalMemberEntity.get();

        NoticeEntity noticeEntity = NoticeEntity.toNoticeInsertEntity(noticeDto,memberEntity);
        noticeRepository.save(noticeEntity);

    }

    public Page<NoticeDto> optionNoticeIdSearchPaging(Long noticeId, Pageable pageable) {

        Page<NoticeEntity> noticeEntityPage = noticeRepository.findByNoticeId(noticeId,pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return  noticeDtoPage;

    }

    public Page<NoticeDto> optionNoticeTitleSearchPaging(String search, Pageable pageable) {
        Page<NoticeEntity> noticeEntityPage = noticeRepository.findByNoticeTitleContaining(search, pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }

    public Page<NoticeDto> optionNoticeContentSearchPaging(String search, Pageable pageable) {
        Page<NoticeEntity> noticeEntityPage = noticeRepository.findByNoticeContentContaining(search, pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }

    public Page<NoticeDto> NoticeAllPagingList(Pageable pageable) {
        Page<NoticeEntity> noticeEntityPage = noticeRepository.findAll(pageable);
        Page<NoticeDto> noticeDtoPage = noticeEntityPage.map(NoticeDto::toNoticeDto);

        return noticeDtoPage;
    }

    //조회수
    @Transactional
    public  void upHit(Long id) {

        noticeRepository.upHitGo(id);
    }

    //공지사항 수정
    @Transactional
    public void noticeUpdateDo(NoticeDto noticeDto, String mEmail) {

        Optional<MemberEntity> optionalMemberEntity
                = memberRepository.findBymEmail(mEmail);

        MemberEntity memberEntity = optionalMemberEntity.get();

        NoticeEntity noticeEntity =NoticeEntity.toNoticeUpdateEntity(noticeDto, memberEntity);
        noticeRepository.save(noticeEntity);


    }

    //공지사항 삭제
    @Transactional
    public void noticeDeleteDo(Long id) {
        noticeRepository.deleteById(id);
    }

    //해당 ID의 공지사항 찾기
    public NoticeDto findByNotice(Long id) {
        Optional<NoticeEntity> optionalNoticeEntity =
                noticeRepository.findById(id);

        if(optionalNoticeEntity.isPresent()){
            return NoticeDto.toNoticeDto(optionalNoticeEntity.get());

        }else {
            return null;
        }

    }
}
