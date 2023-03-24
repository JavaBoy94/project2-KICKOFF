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

    //개인 일정
    @GetMapping("/officialList")
    public String officialList(){

        return "/calendar/memberSchedule/officialList";
    }

    //개인 일정 추가
    @GetMapping("/mySchedulePlus")
    public String mySchedulePlus(Principal principal){
        
        //수정
        //풀캘린더 api로 받아오기

        String scheduleBoard = "내용2";
        LocalDateTime scheduleStart = LocalDateTime.now();
        LocalDateTime scheduleEnd = LocalDateTime.now();
        String scheduleDone = "미완료";

        String name = principal.getName();

        Long id = memberScheduleService.bringLongid(name);

        MemberScheduleDto memberScheduleDto = new MemberScheduleDto();

        memberScheduleDto.setScheduleBoard(scheduleBoard);
        memberScheduleDto.setScheduleStart(scheduleStart);
        memberScheduleDto.setScheduleEnd(scheduleEnd);
        memberScheduleDto.setScheduleDone(scheduleDone);

        memberScheduleService.insertMySchedule(memberScheduleDto,id);

        return "redirect:/memberSchedule/myScheduleView";


    }

    // 개인 일정 조회
    @GetMapping("/myScheduleView")
    public String MyScheduleView(Principal principal, Model model){
        
        // 수정
        String name = principal.getName();

        Long id = memberScheduleService.bringLongid(name);

        List<MemberScheduleDto> memberScheduleDtoList = memberScheduleService.selectMemberSchedule(id);

        model.addAttribute("memberScheduleDtoList", memberScheduleDtoList);

        return "/calendar/memberSchedule/memberScheduleCheck";
        
    }
    
    // 어드민 일정 자세히보기
    @PostMapping("/myScheduleViewDetail")
    public String myScheduleViewDetail(@ModelAttribute("memberScheduleDto")MemberScheduleDto memberScheduleDto, Model model){


        MemberScheduleDto memberScheduleDto1 = memberScheduleService.MemberScheduleDetail(memberScheduleDto.getScheduleId());

        model.addAttribute("memberScheduleDto", memberScheduleDto1);

        return "/calendar/memberSchedule/memberScheduleView";

    }

    // 개인 일정 변경
    @PostMapping("/myScheduleChange")
    public String MyScheduleChange(@ModelAttribute("memberScheduleDto")MemberScheduleDto memberScheduleDto){

        Long scheduleId = memberScheduleDto.getScheduleId();
        Long userId = memberScheduleDto.getMId();

        //풀캘린더 api로 가져오기
        String scheduleBoard = "내용3";
        LocalDateTime scheduleStart = LocalDateTime.now();
        LocalDateTime scheduleEnd = LocalDateTime.now();
        String scheduleDone = "완료";

        MemberScheduleDto memberScheduleDto1 = new MemberScheduleDto();

        memberScheduleDto1.setScheduleId(scheduleId);
        memberScheduleDto1.setScheduleBoard(scheduleBoard);
        memberScheduleDto1.setScheduleStart(scheduleStart);
        memberScheduleDto1.setScheduleEnd(scheduleEnd);
        memberScheduleDto1.setScheduleDone(scheduleDone);
        memberScheduleDto1.setMId(userId);

        memberScheduleService.changeMySchedule(memberScheduleDto1);

        return "redirect:/memberSchedule/myScheduleView";
    }
    
    // 개인 일정 삭제
    @GetMapping("/deleteMemberSchedule/{id}")
    public String deleteMemberSchedule(@PathVariable("id") Long id){

        memberScheduleService.deleteMemberSchedule(id);

        return "redirect:/memberSchedule/myScheduleView";
    }
    
//     어드민 개인 일괄 일정 보기(페이징 ,검색)
//    @GetMapping("/myScheduleList")
//    public String myScheduleList(@PageableDefault(page = 0, size = 4, sort = "schedule_id",
//            direction = Sort.Direction.DESC) Pageable pageable, Model model,
//                                 @RequestParam(required = false,defaultValue = "") String search ,
//                                 @RequestParam(required = false,defaultValue = "") Long id ){
//
//
//        Page<MemberScheduleDto> memberScheduleDtoPage = memberScheduleService.memberScheduleList(id,pageable,search);
//
//        int totalPage = memberScheduleDtoPage.getTotalPages();
//
//        int blockNum = 2;
//
//        int nowPage = memberScheduleDtoPage.getNumber();
//
//        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);
//
//        int endPage = (startPage + blockNum -1 < totalPage ? startPage + blockNum -1 : totalPage);
//
//        model.addAttribute("memberScheduleDtoPage", memberScheduleDtoPage);
//        model.addAttribute("startPage",startPage);
//        model.addAttribute("endPage",endPage);
//
//        return "/calendar/memberSchedule/memberScheduleList";
//    }


//     어드민 개인 일괄 일정 보기(페이징)
    @GetMapping("/myScheduleList")
    public String myScheduleList(@PageableDefault(page = 0, size = 4) Pageable pageable, Model model, Principal principal,
                                 @RequestParam(required = false,defaultValue = "") Long id ){

        String name = principal.getName();

        id = memberScheduleService.bringLongid(name);

        Page<MemberScheduleDto> memberScheduleDtoPage = memberScheduleService.memberScheduleList(pageable);

        int totalPage = memberScheduleDtoPage.getTotalPages();

        int blockNum = 2;

        int nowPage = memberScheduleDtoPage.getNumber();

        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);

        int endPage = (startPage + blockNum -1 < totalPage ? startPage + blockNum -1 : totalPage);

        model.addAttribute("memberScheduleDtoPage", memberScheduleDtoPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/calendar/memberSchedule/memberScheduleList";
    }

    @GetMapping("/memberCalendar")
    public String memberCalendar(){

        return "/calendar/memberSchedule/memberCalendar";
    }
    
    

}
