package org.project2.omwp2.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardFileDto {

    private Long bfileId;
    private String bfileOldName;
    private String bfileNewName;

}
