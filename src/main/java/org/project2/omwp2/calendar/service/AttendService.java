package org.project2.omwp2.calendar.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.repository.AttendRepository;
import org.project2.omwp2.dto.AttendDto;
import org.project2.omwp2.entity.AttendEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendService {

    private final AttendRepository attendRepository;

    private final MemberRepository memberRepository;


    // 회원 이메일로 아이디값 찾아오기
    public Long bringLongid(String name) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findBymEmail(name);

        MemberEntity memberEntity = optionalMemberEntity.get();

        Long id = memberEntity.getMId();

        return id;
    }

    //출퇴근 찍기
    public int CheckAttend(String name) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findBymEmail(name);

        MemberEntity memberEntity = optionalMemberEntity.get();

        Long id = memberEntity.getMId();

        AttendEntity attendEntity = attendRepository.findByLastLine(id);

        if(attendEntity.getAttendEnd()!=null){
            return 1;
        } else {
            return 0;
        }

    }

    //출근 시간 생성
    @Transactional
    public void insertAttend(AttendDto attendDto,Long id) {

        AttendEntity attendEntity = AttendEntity.toAttendEntity(attendDto);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMId(id);

        attendEntity.setMemberEntity(memberEntity);


        attendRepository.save(attendEntity);

    }

    // 퇴근 시간 생성
    @Transactional
    public void goHomeCheck(LocalDateTime goHome, Long id) {

        AttendEntity attendEntity = attendRepository.findByLastLine(id);

        attendEntity.setAttendEnd(goHome);

        attendRepository.save(attendEntity);

    }

    // 출퇴즌 조회 리스트
    public List<AttendDto> attendListById(Long userId) {

        List<AttendDto> attendDtoList = new ArrayList<>();

        List<AttendEntity> attendEntityList = attendRepository.findAllByMId(userId);

        for (AttendEntity attendEntity : attendEntityList){
            attendDtoList.add(AttendDto.toAttendDto(attendEntity));
        }

        return attendDtoList;
    }

    public AttendDto attendDetail(Long id) {

        Optional<AttendEntity> optionalAttendEntity = attendRepository.findById(id);

        AttendEntity attendEntity = optionalAttendEntity.get();

        AttendDto attendDto = AttendDto.toAttendDto(attendEntity);

        return attendDto;

    }


    //출퇴근 조회 페이징
    public Page<AttendDto> attendListSearchPaging(Pageable pageable, Long id) {

//        Page<AttendEntity> attendEntityPage = attendRepository.findAll(pageable);

        Page<AttendEntity> attendEntityPage = attendRepository.findByMMId(id,pageable);

        Page<AttendDto> attendDtoPage = attendEntityPage.map(AttendDto::toAttendDto);

        return attendDtoPage;
    }

    // 근태 내역이 있는지 체크
    public int diva(Long id) {

        List<AttendEntity> attendEntityList = attendRepository.findAllByMId(id);

        if(attendEntityList.isEmpty()){

        return 1;
        } else {

            return 2;
        }

    }

    // 전체 회원 근태 조회 페이징
    public Page<AttendDto> attendListPaging(Pageable pageable) {

        System.out.println("===========================");

        Page<AttendEntity> attendEntityPage = attendRepository.findAll(pageable);


        Page<AttendDto> attendDtoPage = attendEntityPage.map(AttendDto::toAttendDto);

        return attendDtoPage;

    }

    //퇴근 시간 수정
    public void attendEndCheck(Long attendId) {

        Optional<AttendEntity> optionalAttendEntity = attendRepository.findById(attendId);

        AttendEntity attendEntity = optionalAttendEntity.get();

        attendEntity.setAttendEnd(LocalDateTime.now());

        attendRepository.save(attendEntity);


    }

}
