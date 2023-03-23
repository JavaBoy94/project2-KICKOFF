package org.project2.omwp2.approval.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.approval.service.ApprovalService;
import org.project2.omwp2.document.service.DocumentService;
import org.project2.omwp2.dto.ApprovalDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/approval")
public class ApprovalController {
    
    private final ApprovalService approvalService;
    private final DocumentService documentService;
    
    // 결재문서 작성
    @GetMapping("/write")
    public String writeGet(Model model){
        model.addAttribute("ApprovalDto",new ApprovalDto());
        return "approval/write";
    }

    @PostMapping("/write")
    public String writePost(@RequestParam("appContainer")MultipartFile files, ApprovalDto approvalDto)throws IOException{


        approvalService.insertApproval(approvalDto);
        return "redirect:/approval/list";

    }

    // 결재문서 목록
    @GetMapping("/list")
    public String list(Model model){
        List<ApprovalDto> approvalDtoList = approvalService.approvalList();

        model.addAttribute("approvalDtoList",approvalDtoList);

        return "approval/list";
    }

    @GetMapping("/listDetail/{id}")
    public String listDetail(@PathVariable("id") Long addId, Model model){

        ApprovalDto approval = approvalService.findByApproval(addId);

        if (approval !=null) {
            model.addAttribute("approval", approval);

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
    public String listUpdatePost(@RequestParam(value = "appContainer") MultipartFile files, @ModelAttribute ApprovalDto approvalDto) throws IOException {
        approvalService.updateApproval(approvalDto);

        return "redirect:/approval/list";
    }

    // 결재문서 삭제
    @GetMapping("/listDelete/{id}")
    public String listDelete(@PathVariable(value = "id") Long appId){
        approvalService.deleteApproval(appId);
        return "redirect:/approval/list";
    }
}
