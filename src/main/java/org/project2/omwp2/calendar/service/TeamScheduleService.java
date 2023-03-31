package org.project2.omwp2.calendar.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.repository.TeamScheduleRepository;
import org.project2.omwp2.dto.TeamScheduleDto;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.entity.TeamScheduleEntity;
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
public class TeamScheduleService {

    private final TeamScheduleRepository teamScheduleRepository;

    private final MemberRepository memberRepository;

    // 회원 이메일로 아이디값 찾아오기
    public Long bringLongid(String name) {

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findBymEmail(name);

        MemberEntity memberEntity = optionalMemberEntity.get();

        Long id = memberEntity.getMId();

        return id;
    }



    // 팀 스케줄 추가
    @Transactional
    public void insertTeamSchedule(TeamScheduleDto teamScheduleDto, Long id) {

        TeamScheduleEntity teamScheduleEntity = TeamScheduleEntity.toTeamScheduleEntity(teamScheduleDto);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMId(id);

        teamScheduleEntity.setMemberEntity(memberEntity);

        teamScheduleRepository.save(teamScheduleEntity);

    }


    //개인 일정 조회
    public List<TeamScheduleDto> selectTeamSchedule(Long id) {

        List<TeamScheduleDto> teamScheduleDtoList = new ArrayList<>();

        List<TeamScheduleEntity> teamScheduleEntityList = teamScheduleRepository.findAllByMId(id);

        for(TeamScheduleEntity teamScheduleEntity : teamScheduleEntityList){

            teamScheduleDtoList.add(TeamScheduleDto.toTeamScheduleDto(teamScheduleEntity));

        }

        return teamScheduleDtoList;

    }

    @Transactional
    public void changeTeamSchedule(TeamScheduleDto teamScheduleDto1) {

        TeamScheduleEntity teamScheduleEntity = TeamScheduleEntity.toTeamScheduleEntity(teamScheduleDto1);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMId(teamScheduleDto1.getMId());

        teamScheduleEntity.setMemberEntity(memberEntity);

        teamScheduleRepository.save(teamScheduleEntity);

    }

    public void deleteTeamSchedule(Long id) {

        Optional<TeamScheduleEntity> optionalTeamScheduleEntity = teamScheduleRepository.findById(id);

        TeamScheduleEntity teamScheduleEntity = optionalTeamScheduleEntity.get();

        teamScheduleRepository.delete(teamScheduleEntity);
    }

    public Page<TeamScheduleDto> teamScheduleList(Pageable pageable) {

        Page<TeamScheduleEntity> teamScheduleEntities = teamScheduleRepository.findAll(pageable);

        Page<TeamScheduleDto> teamScheduleDtoPage = teamScheduleEntities.map(TeamScheduleDto::toTeamScheduleDto);

        return teamScheduleDtoPage;
    }

    public TeamScheduleDto TeamScheduleDetail(Long scheduleId) {

        Optional<TeamScheduleEntity> optionalTeamScheduleEntity = teamScheduleRepository.findById(scheduleId);

        TeamScheduleEntity teamScheduleEntity = optionalTeamScheduleEntity.get();

        TeamScheduleDto teamScheduleDto = TeamScheduleDto.toTeamScheduleDto(teamScheduleEntity);

        return teamScheduleDto;


    }


    public List<TeamScheduleDto> teamEventListAll() {

        List<TeamScheduleDto> teamScheduleDtoList = new ArrayList<>();

        List<TeamScheduleEntity> teamScheduleEntityList = teamScheduleRepository.findAll();

        for(TeamScheduleEntity teamScheduleEntity : teamScheduleEntityList){

            teamScheduleDtoList.add(TeamScheduleDto.toTeamScheduleDto(teamScheduleEntity));

        }

        return teamScheduleDtoList;
    }

    public void setCalendar(TeamScheduleDto teamScheduleDto, Long id) {

        TeamScheduleEntity teamScheduleEntity = TeamScheduleEntity.toTeamScheduleEntity(teamScheduleDto);

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMId(id);

        teamScheduleEntity.setScheduleDone("N");
        teamScheduleEntity.setMemberEntity(memberEntity);

        teamScheduleRepository.save(teamScheduleEntity);
    }

    //있는 내용인지 확인
    public int findContentName(TeamScheduleDto teamScheduleDto) {

        List<TeamScheduleEntity> teamScheduleEntityList = teamScheduleRepository.findByContentCount(teamScheduleDto.getContent());

            if(teamScheduleEntityList.size()<1){
                return 0;
            } else {
                return 1;
            }

    }

    //팀 일정 수정
    public void updateEvent(TeamScheduleDto teamScheduleDto) {

        TeamScheduleEntity teamScheduleEntity = TeamScheduleEntity.toTeamScheduleEntity(teamScheduleDto);

        //여기문제
        Optional<TeamScheduleEntity> optionalTeamScheduleEntity = teamScheduleRepository.findByContentId(teamScheduleEntity.getContent());

        TeamScheduleEntity teamScheduleEntity1 = optionalTeamScheduleEntity.get();
        

        teamScheduleEntity1.setStart(teamScheduleDto.getStart());
        teamScheduleEntity1.setEnd(teamScheduleDto.getEnd());



        teamScheduleRepository.save(teamScheduleEntity1);

    }
    
    //팀 스케줄 삭제

    public void findContentAndDelete(TeamScheduleDto teamScheduleDto) {


        Optional<TeamScheduleEntity> optionalTeamScheduleEntity = teamScheduleRepository.findByContentId(teamScheduleDto.getContent());

        TeamScheduleEntity teamScheduleEntity = optionalTeamScheduleEntity.get();

        teamScheduleRepository.delete(teamScheduleEntity);


    }





}
