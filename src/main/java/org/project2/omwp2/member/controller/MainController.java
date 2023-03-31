package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.service.BoardService;
import org.project2.omwp2.dto.BoardDto;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.dto.NoticeDto;
import org.project2.omwp2.member.service.IndexService;
import org.project2.omwp2.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final IndexService indexService;

    @GetMapping({"","/","/index"})
    public String index(Model model){

//        공식일정
//        공지사항
        List<NoticeDto> noticeDtoList = indexService.getNoticeList();
//        일반게시판
        List<BoardDto> boardDtoList = indexService.getBoardList();

        model.addAttribute("noticeList",noticeDtoList);
        model.addAttribute("boardList",boardDtoList);

        return "index";
    }

    @GetMapping("/join")
    public String joinOk(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "join";
    }

//    회원가입
    @PostMapping("/joinOk")
    public String joinOk(@Valid MemberDto memberDto, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return "join";
        }

        int rs = memberService.memberJoin(memberDto);

        if (rs!=1) {
            System.out.println("join fail !");
            return "redirect:/join";
        } else {
            System.out.println("join success !");
            return "redirect:/login";
        }

    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }


}
