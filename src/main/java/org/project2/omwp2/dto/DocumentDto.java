package org.project2.omwp2.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DocumentDto {

    public Long docId;
    public String docOrigin;
    public String docSave;

}
