package org.project2.omwp2.comment.controller;


import lombok.RequiredArgsConstructor;
import org.project2.omwp2.board.service.BoardService;
import org.project2.omwp2.comment.service.CommentService;
import org.project2.omwp2.dto.CommentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


        return "redirect:/admin/boardDetail/"+commentDto.getBoardId();


    }
}
