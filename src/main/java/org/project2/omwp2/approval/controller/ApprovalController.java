package org.project2.omwp2.approval.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.approval.service.ApprovalService;
import org.project2.omwp2.document.service.DocumentService;
import org.project2.omwp2.dto.ApprovalDto;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/approval")
public class ApprovalController {
    
    private final ApprovalService approvalService;
    private final DocumentService documentService;
    private final MemberService memberService;
    
    // 결재문서 작성
    @GetMapping("/write")
    public String writeGet(Model model){
        model.addAttribute("ApprovalDto",new ApprovalDto());
        return "approval/write";
    }

    @PostMapping("/write")
    public String writePost(@RequestParam("appContainer")MultipartFile files,
                            ApprovalDto approvalDto, Principal principal)throws IOException{

//        기안서 작성시 현재 로그인한 회원정보 추가
        approvalService.insertApproval(approvalDto,principal);
        return "redirect:/approval/list";

    }

    // 결재문서 목록
    @GetMapping("/list")
    public String list(Model model,  @PageableDefault(page = 0, size = 8, sort = "appId", direction = Sort.Direction.DESC)
    Pageable pageable){

//        List<ApprovalDto> approvalDtoList = approvalService.approvalList();
        Page<ApprovalDto> approvalList = approvalService.getApprovalList(pageable);

        int totalPage = approvalList.getTotalPages();  // 총 페이지 수
        int blockNum = 3;                              // 화면에 표시할 페이지 수 => 2페이지씩 표시
        int nowPage = approvalList.getNumber();        // 현재페이지
        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);
        // 블록의 첫페이지
        // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
        // Math.floor -> 올림

        int endPage = (startPage + blockNum-1 < totalPage ? startPage + blockNum-1 : totalPage);
        // 블록의 마지막 페이지
        // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
        // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

//        model.addAttribute("approvalDtoList",approvalDtoList);
        model.addAttribute("approvalList",approvalList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "approval/list";
    }

//    결재문서 상세
    @GetMapping("/listDetail/{id}")
    public String listDetail(@PathVariable("id") Long addId, Principal principal, Model model){

        String mEmail = principal.getName();
        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        ApprovalDto approval = approvalService.findByApproval(addId);

        if (approval !=null) {
            model.addAttribute("approval", approval);
            model.addAttribute("memberDto",memberDto);
            return "approval/listDetail";
        }else{
            return "redirect:/approval/list";
        }
    }

    // 결재문서 수정 페이지
    @GetMapping("/listUpdate/{id}")
    public String listUpdateGet(@PathVariable("id") Long appId, Model model){

        ApprovalDto approval = approvalService.findByApproval(appId);
        model.addAttribute("approval",approval);
        return "approval/listUpdate";
    }

    // 결재문서 수정 실행
    @PostMapping("/listUpdate")
    public String listUpdatePost(@RequestParam(value = "appContainer") MultipartFile files, @ModelAttribute ApprovalDto approvalDto, Principal principal) throws IOException {
        approvalService.updateApproval(approvalDto, principal);

        return "redirect:/approval/list";
    }

    // 결재문서 삭제
    @GetMapping("/listDelete/{id}")
    public String listDelete(@PathVariable(value = "id") Long appId){
        approvalService.deleteApproval(appId);
        return "redirect:/approval/list";
    }

//    결재문서 승인
    @GetMapping("/approve/{appId}")
    public String approveDo(@PathVariable(value = "appId") Long appId, Model model) {

        ApprovalDto approvalDto = approvalService.approveDo(appId);

        model.addAttribute("approval",approvalDto);

        return "redirect:/approval/listDetail/"+appId;

    }


//    결재문서 반려
    @GetMapping("/reject/{appId}")
    public String rejectDo(@PathVariable(value = "appId") Long appId,
                           @RequestParam(value = "appReason") String reason,
                           Model model) {

        ApprovalDto approvalDto = approvalService.rejectDo(appId, reason);

        model.addAttribute("approval", approvalDto);

        return "redirect:/approval/listDetail/"+appId;
    }
}
