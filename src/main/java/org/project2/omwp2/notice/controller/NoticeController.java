package org.project2.omwp2.notice.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.NoticeDto;
import org.project2.omwp2.notice.service.NoticeService;
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
import java.security.Principal;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항 추가
    @GetMapping("/noticeInsert")
    public String noticeInsert(Model model){

        model.addAttribute("noticeDto",new NoticeDto());

        return "notice/noticeInsert";
    }

    @PostMapping("/noticeInsert")
    public String noticeInsert(@Valid NoticeDto noticeDto, BindingResult bindingResult , Principal principal){

        if(bindingResult.hasErrors()){
            return "notice/noticeInsert";
        }

        String mEmail = principal.getName();

        noticeService.InsertNoticeDo(noticeDto, mEmail);

        return "redirect:/notice/noticePage";

    }

    //공지사항 페이지 및 검색 기능 구현
    @GetMapping("/noticePage")
    public String noticePage(@PageableDefault(page = 0, size = 5, sort = "noticeId",
                            direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "search", required = false) String search,
                            Model model)
    {
        Page<NoticeDto> noticeDtoPage = null;

        Long noticeId = 0L;

        if (type != null && search != null) {
            if (type. equals("noticeId")){
                noticeId = Long.parseLong(search);
                noticeDtoPage = noticeService.optionNoticeIDSearch(noticeId, pageable);
            }
            else if (type.equals("noticeTitle")){
                noticeDtoPage = noticeService.optionNoticeTitleSearch(search, pageable);
            }
            else if (type.equals("noticeContent")){
                noticeDtoPage = noticeService.optionNoticeContentSearch(search, pageable);
            }else {
                noticeDtoPage = noticeService.noticeAll(pageable);
            }
        }else {
            noticeDtoPage = noticeService.noticeAll(pageable);
        }

        int bockNum = 100;
        int nowPage = noticeDtoPage.getNumber() +1;
        int startPage = Math.max(1, noticeDtoPage.getNumber() - bockNum);
        int endPage = noticeDtoPage.getTotalPages();

        model.addAttribute("noticePage", noticeDtoPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "notice/noticePage" ;

    }

    //검색 입력 데이터를 공지사항 페이지로 넘기게 하기
    @GetMapping("/noticeSearch")
    public String search(@RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "search", required = false) String search,
                         RedirectAttributes redirect){

        redirect.addAttribute("type",type);
        redirect.addAttribute("search",search);

        return "redirect:/notice/noticePage";
    }

    @GetMapping("/noticeDetail/{id}")
    public String noticeDetail(@PathVariable Long id ,
                               Model model ){
        noticeService.upHit(id);

        NoticeDto noticeDto = noticeService.findByNotice(id);


        if (noticeDto != null){

            model.addAttribute("notice", noticeDto);

            System.out.println("공지사항 번호: "+noticeDto.getNoticeId()+ " !!!!! ");

        return "notice/noticeDetail";
        }else{
            return "redirect:/notice/noticeInsert";
        }
    }

    @GetMapping("/noticeUpdate/{id}")
    public String noticeUpdate(@PathVariable Long id, Model model){

        NoticeDto noticeDto = noticeService.findByNotice(id);
        model.addAttribute("notice",noticeDto);

        return "notice/noticeUpdate";
    }

    @PostMapping("/noticeUpdate")
    public String noticeUpdateDo(@ModelAttribute NoticeDto noticeDto, Principal principal){

        String mEmail = principal.getName();
        noticeService.noticeUpdateDo(noticeDto, mEmail);

        return "redirect:/notice/noticePage";
    }

    @GetMapping("/noticeDelete/{id}")
    public String noticeDelete(@PathVariable Long id){

        noticeService.noticeDeleteDo(id);

        return "redirect:/notice/noticePage";
    }
}
