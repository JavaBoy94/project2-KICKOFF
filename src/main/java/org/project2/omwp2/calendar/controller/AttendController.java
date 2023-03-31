package org.project2.omwp2.calendar.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.calendar.service.AttendService;
import org.project2.omwp2.dto.AttendDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/attend")
@RequiredArgsConstructor
public class AttendController {

    private final AttendService attendService;
    
    @GetMapping("")
    public String test(){


        return "/calendar/test";
    }

    // 근태 페이지
    @GetMapping("/attend")
    public String attend(Principal principal, Model model){

        String name = principal.getName();

        Long id = attendService.bringLongid(name);

        int asdf = attendService.diva(id);

        if (asdf==1){

            return "/calendar/attend/newAttend";

        } else {

            int check = attendService.CheckAttend(name);


            //1이면 출근보이기, 0이면 퇴근 보이기
            model.addAttribute("check", check);

            return "/calendar/attend/attend";
        }
        // 수정


        
    }


    // 회원 출석(insert)
    @GetMapping("/goWork")
    public String goWork(Principal principal){

        // 수정
        
        String name = principal.getName();

        Long id = attendService.bringLongid(name);

        AttendDto attendDto = new AttendDto();

        attendDto.setAttendStart(LocalDateTime.now());

        attendService.insertAttend(attendDto,id);

        return "redirect:/attend/attend";

    }

    // 회원 퇴근(수정)
    @GetMapping("/goHome")
    public String goHome(Principal principal){

        // 수정

        String name = principal.getName();

        Long id = attendService.bringLongid(name);

        LocalDateTime goHome = LocalDateTime.now();

        attendService.goHomeCheck(goHome, id);

        return "redirect:/attend/attend";
    }

    // 회원 근태 조회
    @GetMapping("/myAttendanceList")
    public String myAttend(Principal principal,Model model){

        // 수정
        String name = principal.getName();

        Long userId = attendService.bringLongid(name);

        List<AttendDto> attendDtoList = attendService.attendListById(userId);

        model.addAttribute("attendDtoList",attendDtoList);

        return "/calendar/attend/myAttendList";
    }



    // 회원 근태 조회 페이징
    @GetMapping("/myAttendancePaging")
    public String myAttendance(@PageableDefault(page = 0,size = 4,sort = "attend_id",
            direction = Sort.Direction.DESC)Pageable pageable, Model model, Principal principal,
            @RequestParam(required = false, defaultValue = "") Long id ){

        //수정

        String name = principal.getName();

        id = attendService.bringLongid(name);

        Page<AttendDto> attendDtoPage = attendService.attendListSearchPaging(pageable, id);

        int totalPage = attendDtoPage.getTotalPages();

        int blockNum = 2;

        int nowPage = attendDtoPage.getNumber();

        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);

        int endPage = (startPage + blockNum -1 < totalPage ? startPage + blockNum -1 : totalPage);

        model.addAttribute("attendDtoPage", attendDtoPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/calendar/attend/myAttendPaging";

    }

    // 전체 회원 근태 조회 페이징
    @GetMapping("/allAttendancePaging")
    public String allAttendancePaging(@PageableDefault(page = 0,size = 4,sort = "attendId",
            direction = Sort.Direction.DESC)Pageable pageable, Model model, Principal principal ) {

        //수정

        Page<AttendDto> attendDtoPage = attendService.attendListPaging(pageable);


        int totalPage = attendDtoPage.getTotalPages();

        int blockNum = 2;

        int nowPage = attendDtoPage.getNumber();

        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);

        int endPage = (startPage + blockNum -1 < totalPage ? startPage + blockNum -1 : totalPage);

        model.addAttribute("attendDtoPage", attendDtoPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/calendar/attend/allAttendPaging";

    }

    // 근태 수정
    @PostMapping("/attendanceDetail")
    public String attendanceDetail(@ModelAttribute("attendDto")AttendDto attendDto, Model model){

        Long id = attendDto.getAttendId();

        AttendDto attendDto1 = attendService.attendDetail(id);

        model.addAttribute("attendDto",attendDto1);

        return "/calendar/attend/myAttendDetail";
    }

    //퇴근 시간 수정
    @PostMapping("/attendUpdate")
    public String attendUpdate(@ModelAttribute("attendDto")AttendDto attendDto, Model model){

        attendService.attendEndCheck(attendDto.getAttendId());



        return "/calendar/teamSchedule/teamCalendar";
    }
    
    
    






}
