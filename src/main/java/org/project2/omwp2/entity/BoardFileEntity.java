package org.project2.omwp2.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boardfile")
public class BoardFileEntity {
    
//   커뮤니티 게시판 첨부파일

    //파일 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bfile_id")
    private Long bfileId;

    //파일 원래이름
    @Column(name = "bfile_old_name", nullable = false)
    private String bfileOldName;

    //파일 새로운이름
    @Column(name = "bfile_new_name", nullable = false)
    private String bfileNewName;

//    커뮤니티 게시판 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity toFileEntity(BoardEntity boardEntity2, String originalName, String newName) {
        BoardFileEntity boardFile = new BoardFileEntity();
        boardFile.setBoardEntity(boardEntity2);
        boardFile.setBfileOldName(originalName);
        boardFile.setBfileNewName(newName);
        return  boardFile;

    }
}
