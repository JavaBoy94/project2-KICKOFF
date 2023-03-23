package org.project2.omwp2.admin.controller;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.service.BoardService;
import org.project2.omwp2.comment.service.CommentService;
import org.project2.omwp2.dto.BoardDto;
import org.project2.omwp2.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping({"","/index"})
    public String Go(){

       return "adminPage";
    }

    @GetMapping("/boardList")
    public String boardPage(@PageableDefault(page = 0, size = 5, sort = "boardId",
            direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "search", required = false) String search,
                            Model model,
                            Principal principal) {

        BoardDto boardDto = new BoardDto();
//        boardDto.setBoardCmcount(boardService.upcount(boardDto.getBoardId()));

        String mEmail = principal.getName();

        Page<BoardDto> boardList;

        Long boardId;
        int bockNum;
        int nowPage;
        int startPage;
        int endPage;

        if (type != null && search != null) {
            if (type.equals("boardId")) {

                boardId = Long.parseLong(search);
                boardList = boardService.optionboardIdSearchPaging(boardId, pageable);

                bockNum = 100;
                nowPage = boardList.getNumber() + 1;
                startPage = Math.max(1, boardList.getNumber() - bockNum);
                endPage = boardList.getTotalPages();

                model.addAttribute("boardList", boardList);
//                model.addAttribute("cmcount",cmcount);
                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);


                return "board/boardPage";
            } else if (type.equals("boardTitle")) {

                boardList = boardService.optionboardTitleSearchPaging(search, pageable);

                bockNum = 100;
                nowPage = boardList.getNumber() + 1;
                startPage = Math.max(1, boardList.getNumber() - bockNum);
                endPage = boardList.getTotalPages();

                model.addAttribute("boardList", boardList);
                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);

                return "board/boardPage";
            } else if (type.equals("boardContent")) {

                boardList = boardService.optionboardContentSearchPaging(search, pageable);

                bockNum = 100;
                nowPage = boardList.getNumber() + 1;
                startPage = Math.max(1, boardList.getNumber() - bockNum);
                endPage = boardList.getTotalPages();

                model.addAttribute("boardList", boardList);
//                model.addAttribute("cmcount",cmcount);
                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);

                return "board/boardPage";
            } else {
                boardList = boardService.BoardAllPagingList(pageable);

                bockNum = 100;
                nowPage = boardList.getNumber() + 1;
                startPage = Math.max(1, boardList.getNumber() - bockNum);
                endPage = boardList.getTotalPages();

                model.addAttribute("boardList", boardList);
//                model.addAttribute("cmcount",cmcount);
                model.addAttribute("nowPage", nowPage);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);

                return "board/boardPage";
            }

        } else {
            boardList = boardService.BoardAllPagingList(pageable);

            bockNum = 100;
            nowPage = boardList.getNumber() + 1;
            startPage = Math.max(1, boardList.getNumber() - bockNum);
            endPage = boardList.getTotalPages();

            model.addAttribute("boardList", boardList);
//            model.addAttribute("cmcount",cmcount);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "board/boardPage";
        }

    }

    @GetMapping("/boardInsert")
    public String boardInsert(Model model){

        model.addAttribute("boardDto", new BoardDto());
        return "board/adminBoardInsert";

    }

    @PostMapping("/boardInsert")
    public String boardInsert(@RequestParam("boardFile") MultipartFile files, BoardDto boardDto, Principal principal) throws IOException {

        String mEmail = principal.getName();


        boardService.insertBoard(boardDto,mEmail);
        return "redirect:/admin/boardList";
    }

    @GetMapping("/boardDetail/{boardId}")
    public String boarddetail(@PathVariable Long boardId, Model model) {

        boardService.upHit(boardId);

        BoardDto board = boardService.findByBoard(boardId);

        if (board != null){

            model.addAttribute("board",board);


            List<CommentDto> commentDtoList = commentService.commentDtoListDo(boardId);
            model.addAttribute("commentDtoList", commentDtoList);

//            boardService.upHit(boardId);
            System.out.println(boardId+" << boardId");

//            model.addAttribute("cmcount",cmcount);
            System.out.println("???????????");

            return "board/adminBoardDetail";
        }else{
            return "redirect:/admin/boardInsert";
        }
    }
//    @GetMapping("/boardUpdate/{id}")
//    public String boardUpdate(@PathVariable("id") Long boardId, Model model) {
//
//        BoardDto boardDto = boardService.findByBoard(boardId);
//        model.addAttribute("board", boardDto);
//        return "admin/adminBoardUpdate";
//
//    }


    @GetMapping("/boardSearch")
    public String boardSearch(@RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "search", required = false) String search,
                              RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("type", type);
        redirectAttributes.addAttribute("search", search);

        return "redirect:/admin/boardList";
    }
}
