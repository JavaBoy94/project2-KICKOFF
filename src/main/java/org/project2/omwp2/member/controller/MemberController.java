package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.member.repository.MemberRepository;
import org.project2.omwp2.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

//    회원정보 상세 (로그인 회원)
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

        String mEmail = principal.getName();  // 로그인한 회원 이메일
        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        model.addAttribute("memberDto",memberDto);

        return "member/memberUpdate";
    }

//    회원수정 실행
    @PostMapping("/updateOk")
    public String memberUpdateDo(@Valid MemberDto memberDto, BindingResult bindingResult,
                                 Principal principal, Model model) throws IOException {

        if(bindingResult.hasErrors()){
//            유효성 검사 에러 발생시
            String mEmail = principal.getName();

            MemberDto memberDto2 = memberService.getMemberDetail(mEmail);
            memberDto.setMAttach(1);
            memberDto.setSaveName(memberDto2.getSaveName());
            model.addAttribute("memberDto",memberDto);

            return "member/memberUpdate";
        }

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

//    관리자메뉴 - 전체 회원목록
    @GetMapping("/memberList")
    public String memberList(Model model, @PageableDefault(page = 0, size = 8, sort = "mId", direction = Sort.Direction.DESC)
                             Pageable pageable) {

        Page<MemberDto> memberList = memberService.getMemberList(pageable);

        int totalPage = memberList.getTotalPages();  // 총 페이지 수
        int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
        int nowPage = memberList.getNumber();        // 현재페이지
        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);
        // 블록의 첫페지이지
        // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
        // Math.floor -> 올림

        int endPage = (startPage + blockNum-1 < totalPage ? startPage + blockNum-1 : totalPage);
        // 블록의 마지막 페이지
        // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
        // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

        model.addAttribute("memberList", memberList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/adminMemberList";
    }

    //    관리자메뉴 - 특정 회원상세
    @GetMapping("/adminMemberDetail/{id}")
    public String memberDetail2(@PathVariable(name = "id") Long id, Model model){

        MemberDto memberDto = memberService.getMemberDetail2(id);
        model.addAttribute("memberDto",memberDto);

        return "member/adminMemberDetail";
    }

    //    관리자메뉴 - 특정 회원수정 페이지
    @GetMapping("/adminMemberUpdate/{id}")
    public String memberUpdate2(@PathVariable(name = "id") Long id, Model model){

        MemberDto memberDto = memberService.getMemberDetail2(id);
        model.addAttribute("memberDto",memberDto);

        return "member/adminMemberUpdate";
    }

    //    관리자메뉴 - 특정 회원수정 실행
    @PostMapping("/adminMemberUpdateOk/{id}")
    public String memberUpdateDo2(@PathVariable(name = "id") Long id, @Valid MemberDto memberDto,
                                  BindingResult bindingResult, Model model) throws IOException {

        if(bindingResult.hasErrors()){
//            유효성 검사 에러 발생시
            MemberDto memberDto2 = memberService.getMemberDetail2(id);
            memberDto.setMAttach(1);
            memberDto.setSaveName(memberDto2.getSaveName());
            model.addAttribute("memberDto",memberDto);

            return "member/adminMemberUpdate";
        }

        int rs = memberService.memberUpdate(memberDto);

        if(rs!=1){
            System.out.println("member update fail !");
            return null;
        }
        return "redirect:/member/adminMemberDetail/"+id;
    }

    //    관리자메뉴 - 특정 회원삭제
    @GetMapping("/adminMemberDelete/{id}")
    public String memberDelete2(@PathVariable(name = "id") Long id, Model model){

        MemberDto memberDto = memberService.getMemberDetail2(id);
        model.addAttribute("memberDto",memberDto);

        return "member/adminMemberDelete";

    }

    //    관리자메뉴 - 특정 회원삭제 실행 => 삭제 성공시 회원목록으로 이동
    @GetMapping("/adminMemberDeleteOk/{id}")
    public String memberDeleteOk2(@PathVariable(name = "id") Long id) {
        MemberDto memberDto = memberService.getMemberDetail2(id);

        int rs = memberService.memberDeleteDo2(id);
        if(rs!=1){
            System.out.println("member update fail !");
            return null;
        }

        return "redirect:/member/memberList";
    }

    //    조직관리 - 팀조회
    @GetMapping("/management")
    public String management(Model model, @PageableDefault(page = 0, size = 8, sort = "mId", direction = Sort.Direction.DESC)
    Pageable pageable) {

        Page<MemberDto> memberList = memberService.getMemberList(pageable);

        int totalPage = memberList.getTotalPages();  // 총 페이지 수
        int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
        int nowPage = memberList.getNumber();        // 현재페이지
        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);
        // 블록의 첫페지이지
        // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
        // Math.floor -> 올림

        int endPage = (startPage + blockNum-1 < totalPage ? startPage + blockNum-1 : totalPage);
        // 블록의 마지막 페이지
        // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
        // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

        model.addAttribute("memberList", memberList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/management";
    }
    
}
