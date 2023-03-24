package org.project2.omwp2.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/calendar",produces = "application/json")
public class CalendarController {

    @GetMapping("/teamSchedule")
    public String memberCalendar(){

        return "/calendar/teamSchedule/teamCalendar";
    }

    @GetMapping("/exCalendar")
    public String exCalendar(){

        return "/calendar/calendar/exCalendar";
    }
}
