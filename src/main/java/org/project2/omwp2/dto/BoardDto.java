package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDto {

    private Long boardId;
    private LocalDateTime boardCreate;
    private LocalDateTime boardUpdate;
    private String boardTitle;
    private String boardContent;
    private int boardHit;
    private int boardCmcount;
    private int boardAttach;

}
