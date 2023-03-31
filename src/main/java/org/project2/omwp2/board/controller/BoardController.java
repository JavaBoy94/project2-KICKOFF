package org.project2.omwp2.board.controller;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/boardList")
    public String boardPage(@PageableDefault(page = 0, size = 5, sort = "boardId",
            direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "search", required = false) String search,
                            Model model) {

//        boardDto.setBoardCmcount(boardService.upcount(boardDto.getBoardId()));


        Page<BoardDto> boardList = null;

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

        } else {
            boardList = boardService.BoardAllPagingList(pageable);
        }


        int bockNum = 100;
        int nowPage = boardList.getNumber() + 1;
        int startPage = Math.max(1, boardList.getNumber() - bockNum);
        int endPage = boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
//        model.addAttribute("comment",comment);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/boardPage";

    }

    @GetMapping("/boardInsert")
    public String boardInsert(Model model) {

        model.addAttribute("boardDto", new BoardDto());
        return "board/BoardInsert";

    }

    @PostMapping("/boardInsert")
    public String boardInsert(@RequestParam("boardFile") MultipartFile files,
                              @Valid BoardDto boardDto, BindingResult bindingResult,
                              Principal principal) throws IOException {

        if(bindingResult.hasErrors()){
            return "board/BoardInsert";
        }

        String mEmail = principal.getName();
        boardService.insertBoard(boardDto, mEmail);
        return "redirect:/board/boardList";
    }

    @GetMapping("/boardDetail/{boardId}/{cm}")
    public String boarddetail(@PathVariable Long boardId,
                              @PathVariable int cm,
                              Model model) {

        if (cm == 0) {
            boardService.upHit(boardId);
        }

        BoardDto board = boardService.findByBoard(boardId);

        if (board != null) {

            model.addAttribute("board", board);


            List<CommentDto> commentDtoList = commentService.commentDtoListDo(boardId);
            model.addAttribute("commentDtoList", commentDtoList);

//            boardService.upHit(boardId);
            System.out.println(boardId + " << boardId");

//            model.addAttribute("cmcount",cmcount);
            System.out.println("???????????");

            return "board/BoardDetail";
        } else {
            return "redirect:/board/boardInsert";
        }
    }


    @GetMapping("/boardUpdate/{id}")
    public String boardUpdate(@PathVariable("id") Long boardId, Model model) {

        BoardDto boardDto = boardService.findByBoard(boardId);
        model.addAttribute("board", boardDto);

        return "board/BoardUpdate";

    }

    @PostMapping("/boardUpdate")
    public String boardUpdateDo(@RequestParam(value = "bfileNewName", required = false) String bfileNewName,
                                @ModelAttribute BoardDto boardDto, Principal principal) {

        String mEmail = principal.getName();
        System.out.println("boardContent "+boardDto.getBoardContent());

        boardService.boardUpdateDo(boardDto, mEmail);

        return "redirect:/board/boardList";

    }

    @GetMapping("/boardDelete/{id}")
    public String boardDelete(@PathVariable(value = "id") Long productId) {
        boardService.boardDeleteDo(productId);

        return "redirect:/board/boardList";
    }


    @GetMapping("/boardSearch")
    public String boardSearch(@RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "search", required = false) String search,
                              RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("type", type);
        redirectAttributes.addAttribute("search", search);

        return "redirect:/board/boardList";
    }

}
