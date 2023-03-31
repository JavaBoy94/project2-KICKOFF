package org.project2.omwp2.comment.controller;


import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.service.BoardService;
import org.project2.omwp2.comment.service.CommentService;
import org.project2.omwp2.dto.BoardDto;
import org.project2.omwp2.dto.CommentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/commentWrite")
    public String commentWrite(@ModelAttribute CommentDto commentDto,
                               Model model
                               ,Principal principal
                                ){

        String mEmail = principal.getName();

        Long result = commentService.insertCommentDo(commentDto,mEmail);
        System.out.println("mEmail"+commentDto.getMEmail());
        System.out.println("boardId"+commentDto.getBoardId());


//         게시글 번호를 추가 -> 리뷰번호가 아니라
//         게시글 번호를 html에서 가져와서
        if (result != 0){
            boardService.UpCommentCount(commentDto.getBoardId());
        }

       List<CommentDto> commentDtoList =
               commentService.commentDtoListDo(commentDto.getBoardId());

        model.addAttribute("commentDtoList", commentDtoList);


        return "redirect:/board/boardDetail/"+commentDto.getBoardId()+"/"+1;


    }

    @GetMapping("/commentUpdate/{commentId}")
    public String commentUpdate(@PathVariable("commentId") Long commentId, Model model ){

        Long boardId = commentService.findBoardId(commentId);
        BoardDto boardDto = boardService.findByBoard(boardId);

        model.addAttribute("board",boardDto);

        CommentDto commentDto = commentService.findByCommet(commentId);
        model.addAttribute("comment",commentDto);

        return "commentUpdate";
    }

    @PostMapping("/commentUpdate")
    public String commentUpdateDo(@ModelAttribute CommentDto commentDto, Principal principal, Model model){

        String mEmail = principal.getName();
        commentService.commentUpdateDo(commentDto,mEmail);

        return "redirect:/board/boardDetail/"+commentDto.getBoardId()+"/"+1;
    }


    @GetMapping("/commentDelete/{id}")
    public String commentDelete(@PathVariable(value = "id") Long commentId,
                                Principal principle) {
        String mEmail = principle.getName();

        Long boardId = commentService.findBoardId(commentId);
        int result = commentService.commetDeleteDo(commentId,mEmail);
        if(result == 0){
            return null;
        }

        boardService.downCommentCount(boardId);

        return "redirect:/board/boardDetail/"+boardId+"/"+1;
    }


}
