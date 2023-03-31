package org.project2.omwp2.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.service.MemberScheduleService;
import org.project2.omwp2.calendar.service.TeamScheduleService;
import org.project2.omwp2.dto.MemberScheduleDto;
import org.project2.omwp2.dto.TeamScheduleDto;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api",produces = "application/json")
public class ApiController {

    private final TeamScheduleService teamScheduleService;

    private final MemberScheduleService memberScheduleService;


//    @GetMapping("/events")
//    public List<TeamScheduleDto> teamEvent(){
//
//        List<TeamScheduleDto> teamScheduleDtoList = teamScheduleService.teamEventListAll();
//
//        return teamScheduleDtoList;
//    }

    //팀 일정 추가 및 수정
    @PostMapping("/calendar")
    public List<TeamScheduleDto> teamScheduleDtoList(@ModelAttribute TeamScheduleDto teamScheduleDto, Principal principal){


        String name = principal.getName();

        Long id =teamScheduleService.bringLongid(name);

        int find = teamScheduleService.findContentName(teamScheduleDto);

        if(find==1){

            teamScheduleService.updateEvent(teamScheduleDto);

            return teamScheduleService.teamEventListAll();
        } else {


            teamScheduleService.setCalendar(teamScheduleDto,id);

            return teamScheduleService.teamEventListAll();

        }



    }

    //팀 일정 새로고침
    @GetMapping("/calendar")
    public List<TeamScheduleDto> getCalendar(){

        return teamScheduleService.teamEventListAll();
    }

    //개인 일정 추가 및 수정
    @PostMapping("/myCalendar")
    public List<MemberScheduleDto> memberScheduleDtoList(@ModelAttribute MemberScheduleDto memberScheduleDto, Principal principal){


        String name = principal.getName();

        Long id =memberScheduleService.bringLongid(name);

        int find = memberScheduleService.findContentName(memberScheduleDto);

        if(find==1){

            memberScheduleService.updateEvent(memberScheduleDto);

            return memberScheduleService.memberEventListAll(id);
        } else {


            memberScheduleService.setCalendar(memberScheduleDto,id);

            return memberScheduleService.memberEventListAll(id);

        }


    }

    //개인 일정 새로고침
    @GetMapping("/myCalendar")
    public List<MemberScheduleDto> getMyCalendar(Principal principal){

        String name = principal.getName();

        Long id = memberScheduleService.bringLongid(name);

        return memberScheduleService.memberEventListAll(id);
    }


    //팀 일정 삭제
    @PostMapping("/deleteCalendar")
    public List<TeamScheduleDto> deleteTeamSchedule(@ModelAttribute TeamScheduleDto teamScheduleDto, Principal principal){


        teamScheduleService.findContentAndDelete(teamScheduleDto);


        return teamScheduleService.teamEventListAll();

    }

    //개인 일정 삭제
    @PostMapping("/mydeleteCalendar")
    public List<MemberScheduleDto> deleteMemberSchedule(@ModelAttribute MemberScheduleDto memberScheduleDto, Principal principal){


        memberScheduleService.findContentAndDelete(memberScheduleDto);


        String name = principal.getName();

        Long id = memberScheduleService.bringLongid(name);

        return memberScheduleService.memberEventListAll(id);

    }



}
