package org.project2.omwp2.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.service.MemberScheduleService;
import org.project2.omwp2.calendar.service.TeamScheduleService;
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


    @GetMapping("/events")
    public List<TeamScheduleDto> teamEvent(){

        List<TeamScheduleDto> teamScheduleDtoList = teamScheduleService.teamEventListAll();

        return teamScheduleDtoList;
    }

    @PostMapping("/calendar")
    public List<TeamScheduleDto> teamScheduleDtoList(@ModelAttribute TeamScheduleDto teamScheduleDto, Principal principal){


        String name = principal.getName();

        Long id =teamScheduleService.bringLongid(name);


        teamScheduleService.setCalendar(teamScheduleDto,id);

        return teamScheduleService.teamEventListAll();

    }

    @GetMapping("/calendar")
    public List<TeamScheduleDto> getCalendar(){

        return teamScheduleService.teamEventListAll();
    }



}
