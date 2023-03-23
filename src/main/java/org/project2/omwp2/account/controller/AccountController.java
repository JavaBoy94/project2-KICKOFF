package org.project2.omwp2.account.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.account.service.AccountService;
import org.project2.omwp2.dto.AccountDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    // 수입지출 내역 리스트(회계관련 메인페이지)
    @GetMapping("/list")
    public String accountList(Model model){

        List<AccountDto> accountDtoList = accountService.accountList();

        model.addAttribute("accountDtoList", accountDtoList);

        return "account/accountList";
    }

    // 등록페이지 이동
    @GetMapping("/insert")
    public String accountInsert(Model model){

        model.addAttribute("accountDto", new AccountDto());
        return "account/accountInsert";
    }

    // 게시글 작성 실행
    @PostMapping("/insertOk")
    public String accountInsertOk(@ModelAttribute AccountDto accountDto, Model model, Principal principal){

        String mEmail = principal.getName();

        accountService.accountInsert(accountDto,mEmail);

        model.addAttribute("accountDto", new AccountDto());
        return "redirect:/account/list";
    }

    // 게시글 상세 페이지 이동
    @GetMapping("/detail/{acId}")
    public String acDetail(@PathVariable Long acId, Model model){

        AccountDto accountDto = accountService.accountDetail(acId);
        if(accountDto != null){
            model.addAttribute("accountDto", accountDto);
            return "account/accountDetail";
        }else{
            return "redirect:/account/list";
        }

    }

    // 게시글 수정 이동
    @GetMapping("/update/{acId}")
    public String acUpdate(@PathVariable Long acId, Model model){

        AccountDto accountDto = accountService.accountUpdate(acId);

        if(accountDto != null){
            model.addAttribute("accountDto", accountDto);
            return "account/accountUpdate";
        }else{
            return "redirect:/account/list";
        }
    }

    // 게시글 수정 실행
    @PostMapping("/updateOk")
    public String acUpdateOk(@ModelAttribute AccountDto accountDto){

        int rs = accountService.accountUpdateOk(accountDto);
        if(rs != 1) {
            return null;
        }
        return "redirect:/account/detail/" + accountDto.getAcId();
    }

    // 게시글 삭제
    @PostMapping("/delete/{acId}")
    public String acDeleteOk(@PathVariable Long acId){

        int rs = accountService.accountDelete(acId);

        if(rs != 1){
            return null;
        }

        return "redirect:/account/list";
    }

}
