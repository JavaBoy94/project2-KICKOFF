package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

//    회원정보 상세
    @GetMapping("/detail")
    public String memberDetail(Model model, Principal principal){

        String mEmail = principal.getName();
        System.out.println(mEmail+"<<<< member Email");

        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        model.addAttribute("memberDto",memberDto);

        return "member/memberDetail";
    }

//    회원수정 페이지
    @GetMapping("/update")
    public String memberUpdate(Model model, Principal principal){

        String mEmail = principal.getName();
        System.out.println(mEmail+"<<<< member Email");

        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        model.addAttribute("memberDto",memberDto);

        return "member/memberUpdate";
    }

//    회원수정 실행
    @PostMapping("/updateOk")
    public String memberUpdateDo(@ModelAttribute MemberDto memberDto) throws IOException {

        int rs = memberService.memberUpdate(memberDto);

        if(rs!=1){
            System.out.println("member update fail !");
            return null;
        }
        return "redirect:/member/detail";
    }

//    회원삭제 페이지
    @GetMapping("/delete")
    public String memberDelete(Model model, Principal principal){

        String mEmail = principal.getName();
        System.out.println(mEmail+"<<<< member Email");

        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        model.addAttribute("memberDto",memberDto);

        return "member/memberDelete";

    }

//    회원탈퇴 실행 => 탈퇴 성공시 로그인 페이지로 이동 
    @GetMapping("/deleteOk")
    public String memberDeleteOk(Principal principal) {
        String mEmail = principal.getName();
        int rs = memberService.memberDeleteDo(mEmail);
        if(rs!=1){
            System.out.println("member update fail !");
            return null;
        }

        return "login";
    }
}
