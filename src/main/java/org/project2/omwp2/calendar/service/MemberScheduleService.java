package org.project2.omwp2.calendar.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.repository.MemberScheduleRepository;
import org.project2.omwp2.dto.MemberScheduleDto;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.entity.MemberScheduleEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberScheduleService {

    private final MemberScheduleRepository memberScheduleRepository;

    private final MemberRepository memberRepository;

    // 회원 이메일로 아이디값 찾아오기
    public Long bringLongid(String name) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findBymEmail(name);

        MemberEntity memberEntity = optionalMemberEntity.get();

        Long id = memberEntity.getMId();

        return id;
    }

    // 개인 스케줄 추가
    @Transactional
    public void insertMySchedule(MemberScheduleDto memberScheduleDto,Long id) {

        MemberScheduleEntity memberScheduleEntity = MemberScheduleEntity.toMemberScheduleEntity(memberScheduleDto);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMId(id);

        memberScheduleEntity.setMemberEntity(memberEntity);

        memberScheduleRepository.save(memberScheduleEntity);

    }

    // 개인 스케줄 조회
    public List<MemberScheduleDto> selectMemberSchedule(Long id) {

        List<MemberScheduleDto> memberScheduleDtoList = new ArrayList<>();

        List<MemberScheduleEntity> memberScheduleEntityList = memberScheduleRepository.findAllByMId(id);

        for(MemberScheduleEntity memberScheduleEntity : memberScheduleEntityList){

            memberScheduleDtoList.add(MemberScheduleDto.toMemberScheduleDto(memberScheduleEntity));

        }

        return memberScheduleDtoList;

    }

    // 개인 스케줄 변경
    @Transactional
    public void changeMySchedule(MemberScheduleDto memberScheduleDto1) {

        MemberScheduleEntity memberScheduleEntity = MemberScheduleEntity.toMemberScheduleEntity(memberScheduleDto1);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMId(memberScheduleDto1.getMId());

        memberScheduleEntity.setMemberEntity(memberEntity);

        memberScheduleRepository.save(memberScheduleEntity);
    }

    // 개인 일정 삭제
    @Transactional
    public void deleteMemberSchedule(Long id) {

        Optional<MemberScheduleEntity> optionalMemberScheduleEntity = memberScheduleRepository.findById(id);

        MemberScheduleEntity memberScheduleEntity = optionalMemberScheduleEntity.get();

        memberScheduleRepository.delete(memberScheduleEntity);
    }

    // 관리자 개인 일정 조회(페이징, 검색)
//    public Page<MemberScheduleDto> memberScheduleList(Long id, Pageable pageable, String search) {
//
//        Page<MemberScheduleEntity> memberScheduleEntities = memberScheduleRepository.findByScheduleBoardContainingOrScheduleIdContaining(pageable, search, id);
//
//        Page<MemberScheduleDto> memberScheduleDtoPage = memberScheduleEntities.map(MemberScheduleDto::toMemberScheduleDto);
//
//        return memberScheduleDtoPage;
//
//    }

    // 관리자 개인 일정 조회(페이징)
    public Page<MemberScheduleDto> memberScheduleList(Pageable pageable) {

        Page<MemberScheduleEntity> memberScheduleEntities = memberScheduleRepository.findAll(pageable);

        Page<MemberScheduleDto> memberScheduleDtos = memberScheduleEntities.map(MemberScheduleDto::toMemberScheduleDto);

        return memberScheduleDtos;

    }

    // 관리자 개인 일정 디테일
    public MemberScheduleDto MemberScheduleDetail(Long scheduleId) {

        Optional<MemberScheduleEntity> optionalMemberScheduleEntity = memberScheduleRepository.findById(scheduleId);

        MemberScheduleEntity memberScheduleEntity = optionalMemberScheduleEntity.get();

        MemberScheduleDto memberScheduleDto = MemberScheduleDto.toMemberScheduleDto(memberScheduleEntity);

        return memberScheduleDto;

    }



}
