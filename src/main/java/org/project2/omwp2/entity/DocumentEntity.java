package org.project2.omwp2.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name= "document")
public class DocumentEntity {

    // 첨부서류 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id", unique = true, nullable = false)
    public Long docId;

    // 원본파일명
    @Column(name = "doc_origin",nullable = false)
    public String docOrigin;

    // 저장파일명
    @Column(name = "doc_save",nullable = false)
    public String docSave;

    // 회계내역
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ac_id")
    private AccountEntity accountEntity;

    // 결재
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private ApprovalEntity approvalEntity;

    public static DocumentEntity toDocumentEntity(ApprovalEntity approvalEntity,String docOrigin,String docSave){
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setApprovalEntity(approvalEntity);
        documentEntity.setDocOrigin(docOrigin);
        documentEntity.setDocSave(docSave);
        return documentEntity;
    }

}
