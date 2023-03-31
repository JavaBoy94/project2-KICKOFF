package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.service.BoardService;
import org.project2.omwp2.dto.BoardDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BoardService boardService;

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
    public String memberList(@PageableDefault(page = 0, size = 8, sort = "mCreate", direction = Sort.Direction.DESC)
                             Pageable pageable, Model model,
                             @RequestParam(value = "type",required = false) String type,
                             @RequestParam(value = "search",required = false) String search) {

        Page<MemberDto> memberList = memberService.getMemberList(pageable);

//        검색조회
        if(type != null && search != null) {

            if(type.equals("mName")) {
//                이름으로 검색
                memberList = memberService.findMemberName(search,pageable);
            } else if (type.equals("mEmail")) {
//                이메일로 검색
                memberList = memberService.findMemberEmail(search,pageable);
            } else if (type.equals("mTel")) {
//                연락처로 검색
                memberList = memberService.findMemberTel(search,pageable);
            }

        }

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

    //    관리자메뉴 - 회워목록 내 검색
    @GetMapping("/adminMemberSearch")
    public String adminMemberSearch(@RequestParam(value = "type",required = false) String type,
                                    @RequestParam(value = "search",required = false) String search,
                                    RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("type",type);
        redirectAttributes.addAttribute("search",search);

        return "redirect:/member/memberList";
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

        System.out.println(memberDto.getMRole()+"<<< Role");
        System.out.println(memberDto.getMDept()+"<<< Dept");

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

//    작성글 관리 => 해당 회원이 쓴 글 목록
    @GetMapping("/myBoardList")
    public String myBoardList(@PageableDefault(page = 0, size = 10, sort = "board_id",
                            direction = Sort.Direction.DESC) Pageable pageable,
                            Model model, Principal principal) {

            String mEmail = principal.getName();
            Long mId = memberRepository.findBymEmail(mEmail).get().getMId();

            Page<BoardDto> boardList = null;

            boardList = boardService.myBoardListDo(mId, pageable);

            int totalPage = boardList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수
            int nowPage = boardList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫페지이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

            model.addAttribute("boardList", boardList);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/myBoardList";
    }

    //    조직관리 - 포지션별 조회
    @GetMapping("/position")
    public String position(Model model,
                           @RequestParam("mPosition") String mPosition,
                           @PageableDefault(page = 0, size = 8, sort = "mId", direction = Sort.Direction.DESC) Pageable pageable) {

        // 상관없음(MULTI)
        if (mPosition.equals("MULTI") || mPosition.equals("multi")) {
            List<MemberDto> memberDtoList = memberService.MultiMemberList(mPosition);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberPositionList(mPosition, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/positionMulti";
        }
        // 공격수(ST)
        if (mPosition.equals("ST") || mPosition.equals("st")) {
            List<MemberDto> memberDtoList = memberService.StMemberList(mPosition);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberPositionList(mPosition, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/positionSt";
        }
        // 미드필더(MF)
        if (mPosition.equals("MF") || mPosition.equals("mf")) {
            List<MemberDto> memberDtoList = memberService.MfMemberList(mPosition);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberPositionList(mPosition, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/positionMf";
        }
        // 수비수(DF)
        if (mPosition.equals("DF") || mPosition.equals("df")) {
            List<MemberDto> memberDtoList = memberService.DfMemberList(mPosition);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberPositionList(mPosition, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/positionDf";
        }
        // 골키퍼(GK)
        if (mPosition.equals("GK") || mPosition.equals("gk")) {
            List<MemberDto> memberDtoList = memberService.GkMemberList(mPosition);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberPositionList(mPosition, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/positionGk";
        }
        return null;

    }

    //    조직관리 - 회원구분별 조회
    @GetMapping("/dept")
    public String dept(Model model,
                       @RequestParam("mDept") String mDept,
                       @PageableDefault(page = 0, size = 8, sort = "mId", direction = Sort.Direction.DESC) Pageable pageable) {

        // 회장(CP)
        if (mDept.equals("CP") || mDept.equals("cp")) {
            List<MemberDto> memberDtoList = memberService.CpMemberList(mDept);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberDeptList(mDept, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/deptCp";
        }

        // 부회장(VP)
        if (mDept.equals("VP") || mDept.equals("vp")) {
            List<MemberDto> memberDtoList = memberService.VpMemberList(mDept);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberDeptList(mDept, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/deptVp";
        }

        // 총무(GA)
        if (mDept.equals("GA") || mDept.equals("ga")) {
            List<MemberDto> memberDtoList = memberService.GaMemberList(mDept);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberDeptList(mDept, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/deptGa";
        }

        // 매니저(MANAGER)
        if (mDept.equals("MANAGER") || mDept.equals("manager")) {
            List<MemberDto> memberDtoList = memberService.ManagerMemberList(mDept);
            model.addAttribute("memberList", memberDtoList);

            Page<MemberDto> memberList = memberService.getMemberDeptList(mDept, pageable);

            int totalPage = memberList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
            int nowPage = memberList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫 페이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
            // Math.floor -> 올림

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
            // 블록의 마지막 페이지
            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

            model.addAttribute("memberList", memberList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/deptManager";
        }

        // 일반회원, 기본(MEMBER)
//        if (mDept.equals("MEMBER") || mDept.equals("member")) {
//            List<MemberDto> memberDtoList = memberService.MemberMemberList(mDept);
//            model.addAttribute("memberList", memberDtoList);
//
//            Page<MemberDto> memberList = memberService.getMemberDeptList(mDept, pageable);
//
//            int totalPage = memberList.getTotalPages();  // 총 페이지 수
//            int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
//            int nowPage = memberList.getNumber();        // 현재페이지
//            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
//            // 블록의 첫 페이지
//            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
//            // Math.floor -> 올림
//
//            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);
//            // 블록의 마지막 페이지
//            // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
//            // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)
//
//            model.addAttribute("memberList", memberList);
//            model.addAttribute("startPage", startPage);
//            model.addAttribute("endPage", endPage);
//
//            return "member/deptMember";
//        }
        return null;
    }

    //    조직관리 - 팀배치
    @GetMapping("/managementPlace")
    public String managementPlace(Model model, @PageableDefault(page = 0, size = 8, sort = "mId", direction = Sort.Direction.DESC)
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

        return "member/managementPlace";
    }
}
