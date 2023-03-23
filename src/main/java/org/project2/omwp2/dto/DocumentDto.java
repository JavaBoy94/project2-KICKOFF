package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.DocumentEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DocumentDto {

    // 첨부서류 ID
    public Long docId;

    // 원본파일명
    public String docOrigin;

    // 저장파일명
    public String docSave;

    public static DocumentDto toDocumentDto(DocumentEntity documentEntity){
        DocumentDto documentDto = new DocumentDto();
        documentDto.setDocSave(documentEntity.getDocSave());
        return documentDto;
    }
}
