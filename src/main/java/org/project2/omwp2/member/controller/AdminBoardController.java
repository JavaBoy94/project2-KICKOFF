package org.project2.omwp2.member.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.service.BoardService;
import org.project2.omwp2.dto.BoardDto;
import org.project2.omwp2.dto.NoticeDto;
import org.project2.omwp2.notice.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class AdminBoardController {

    private final BoardService boardService;
    private final NoticeService noticeService;

    @GetMapping("/adminBoardList")
    public String adminBoardList(@PageableDefault(page = 0, size = 10, sort = "boardId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "search", required = false) String search,
                                 Model model) {

//        boardDto.setBoardCmcount(boardService.upcount(boardDto.getBoardId()));


        Page<BoardDto> boardList = boardService.BoardAllPagingList(pageable);

        Long boardId = 0L;

        if (type != null && search != null) {
            if (type.equals("boardId")) {
                boardId = Long.parseLong(search);
                boardList = boardService.optionboardIdSearchPaging(boardId, pageable);

            } else if (type.equals("boardTitle")) {
                boardList = boardService.optionboardTitleSearchPaging(search, pageable);

            } else if (type.equals("boardContent")) {
                boardList = boardService.optionboardContentSearchPaging(search, pageable);

            }

        }

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

        return "admin/adminBoardList";

    }
//     관리자메뉴 - 일반게시판 검색
    @GetMapping("/adminBoardSearch")
    public String boardSearch(@RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "search", required = false) String search,
                              RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("type", type);
        redirectAttributes.addAttribute("search", search);

        return "redirect:/board/adminBoardList";
    }
//    관리자메뉴 - 공지사항 목록
    @GetMapping("/adminNoticeList")
    public String adminNoticeList(@PageableDefault(page = 0, size = 10, sort = "noticeId",
            direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "search", required = false) String search,
                                 Model model) {

//        boardDto.setBoardCmcount(boardService.upcount(boardDto.getBoardId()));


        Page<NoticeDto> noticeList = noticeService.NoticeAllPagingList(pageable);

        Long noticeId = 0L;

        if (type != null && search != null) {
            if (type.equals("noticeId")) {
                noticeId = Long.parseLong(search);
                noticeList = noticeService.optionNoticeIdSearchPaging(noticeId, pageable);

            } else if (type.equals("noticeTitle")) {
                noticeList = noticeService.optionNoticeTitleSearchPaging(search, pageable);

            } else if (type.equals("noticeContent")) {
                noticeList = noticeService.optionNoticeContentSearchPaging(search, pageable);

            }

        }

        int totalPage = noticeList.getTotalPages();  // 총 페이지 수
        int blockNum = 3;                            // 화면에 표시할 페이지 수
        int nowPage = noticeList.getNumber();        // 현재페이지
        int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
        // 블록의 첫페지이지
        // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
        // Math.floor -> 올림

        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/adminNoticeList";
    }

    //     관리자메뉴 - 공지사항 검색
    @GetMapping("/adminNoticeSearch")
    public String noticeSearch(@RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "search", required = false) String search,
                              RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("type", type);
        redirectAttributes.addAttribute("search", search);

        return "redirect:/board/adminNoticeList";
    }
}
