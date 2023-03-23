package org.project2.omwp2.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.service.TeamScheduleService;
import org.project2.omwp2.dto.TeamScheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/teamSchedule")
@RequiredArgsConstructor
public class TeamScheduleController {

    private final TeamScheduleService teamScheduleService;


    //팀 일정 추가
    @GetMapping("/teamSchedulePlus")
    public String teamSchedulePlus(Principal principal){

        //수정
        //풀캘린더 api로 받아오기

        String scheduleBoard = "내용1";
        LocalDateTime scheduleStart = LocalDateTime.now();
        LocalDateTime scheduleEnd = LocalDateTime.now();
        String scheduleDone = "미완료";

        String name = principal.getName();

        Long id = teamScheduleService.bringLongid(name);

        TeamScheduleDto teamScheduleDto = new TeamScheduleDto();

        teamScheduleDto.setScheduleBoard(scheduleBoard);
        teamScheduleDto.setScheduleStart(scheduleStart);
        teamScheduleDto.setScheduleEnd(scheduleEnd);
        teamScheduleDto.setScheduleDone(scheduleDone);

        teamScheduleService.insertTeamSchedule(teamScheduleDto,id);

        return "redirect:/teamSchedule/teamScheduleView";


    }
    
    //팀 일정 조회
    @GetMapping("/teamScheduleView")
    public String teamScheduleView(Principal principal, Model model){

        // 수정 security 값 가져오기
        String name = principal.getName();

        Long id = teamScheduleService.bringLongid(name);

        List<TeamScheduleDto> teamScheduleDtoList = teamScheduleService.selectTeamSchedule(id);

        model.addAttribute("teamScheduleDtoList",teamScheduleDtoList);

        return "/calendar/teamSchedule/teamScheduleCheck";

        
    }

    //팀 일정 변경
    @PostMapping("/teamScheduleChange")
    public String teamScheduleChange(@ModelAttribute("teamScheduleDto")TeamScheduleDto teamScheduleDto){

        Long scheduleId = teamScheduleDto.getScheduleId();
        Long userId = teamScheduleDto.getMId();

        //풀캘린더 api로 가져오기
        String scheduleBoard = "내용2";
        LocalDateTime scheduleStart = LocalDateTime.now();
        LocalDateTime scheduleEnd = LocalDateTime.now();
        String scheduleDone = "완료";

        TeamScheduleDto teamScheduleDto1 = new TeamScheduleDto();

        teamScheduleDto1.setScheduleId(scheduleId);
        teamScheduleDto1.setScheduleBoard(scheduleBoard);
        teamScheduleDto1.setScheduleStart(scheduleStart);
        teamScheduleDto1.setScheduleEnd(scheduleEnd);
        teamScheduleDto1.setScheduleDone(scheduleDone);
        teamScheduleDto1.setMId(userId);

        teamScheduleService.changeTeamSchedule(teamScheduleDto1);

        return "redirect:/teamSchedule/teamScheduleView";

    }

    //팀 일정 삭제
    @GetMapping("/deleteTeamSchedule/{id}")
    public String deleteTeamSchedule(@PathVariable("id") Long id){

        teamScheduleService.deleteTeamSchedule(id);

        return "redirect:/teamSchedule/teamScheduleView";

    }

    //관리자 팀 일정 리스트
    @GetMapping("/myScheduleList")
    public String teamScheduleList(@PageableDefault(page = 0, size = 4) Pageable pageable, Model model, Principal principal,
                                   @RequestParam(required = false,defaultValue = "") Long id ){

        String name = principal.getName();

        id = teamScheduleService.bringLongid(name);

        Page<TeamScheduleDto> teamScheduleDtoPage = teamScheduleService.teamScheduleList(pageable);

        int totalPage = teamScheduleDtoPage.getTotalPages();

        int blockNum = 2;

        int nowPage = teamScheduleDtoPage.getNumber();

        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);

        int endPage = (startPage + blockNum -1 < totalPage ? startPage + blockNum -1 : totalPage);

        model.addAttribute("teamScheduleDtoPage", teamScheduleDtoPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/calendar/teamSchedule/teamScheduleList";

    }


    // 어드민 일정 자세히보기
    @PostMapping("/myScheduleViewDetail")
    public String myScheduleViewDetail(@ModelAttribute("teamScheduleDto")TeamScheduleDto teamScheduleDto, Model model){


        TeamScheduleDto teamScheduleDto1 = teamScheduleService.TeamScheduleDetail(teamScheduleDto.getScheduleId());

        model.addAttribute("teamScheduleDto", teamScheduleDto1);

        return "/calendar/teamSchedule/teamScheduleView";

    }
    
    

}
