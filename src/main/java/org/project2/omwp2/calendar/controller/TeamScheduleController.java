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
import java.util.List;

@Controller
@RequestMapping("/teamSchedule")
@RequiredArgsConstructor
public class TeamScheduleController {

    private final TeamScheduleService teamScheduleService;



    @GetMapping("/teamSchedule")
    public String memberCalendar(Model model){

        model.addAttribute("key","1");

        return "/calendar/teamSchedule/teamCalendar";
    }

}
