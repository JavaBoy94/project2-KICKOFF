package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping({"","/","/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/join")
    public String joinOk(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "join";
    }

    @PostMapping("/join")
    public String joinOk(MemberDto memberDto) throws IOException {
        int rs = memberService.memberJoin(memberDto);

        if (rs!=1) {
            System.out.println("join fail !");
            return "redirect:/join";
        } else {
            System.out.println("join success !");
            return "redirect:/login";
        }

    }

}
