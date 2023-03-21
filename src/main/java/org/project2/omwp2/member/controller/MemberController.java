package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

//    회원정보 상세
    @GetMapping("/detail")
    public String memberDetail(Model model, Principal principal){

//        String mEmail = principal.getName();
        String mEmail = "m1@gmail.com";
        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        model.addAttribute("memberDto",memberDto);

        return "member/memberDetail";
    }

}
