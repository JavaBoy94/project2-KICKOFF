package org.project2.omwp2.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.service.MemberScheduleService;
import org.project2.omwp2.dto.MemberScheduleDto;
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
@RequestMapping("/memberSchedule")
@RequiredArgsConstructor
public class MemberScheduleController {

    private final MemberScheduleService memberScheduleService;




    //개인 일정 달력
    @GetMapping("/memberSchedule")
    public String memberCalendar(Model model){

        model.addAttribute("key","1");

        return "/calendar/memberSchedule/memberCalendar";
    }
    
    

}
