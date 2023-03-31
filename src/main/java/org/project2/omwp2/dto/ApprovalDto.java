package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.ApprovalEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApprovalDto {

    // 문서 ID
    private Long appId;

    // 구분 (expend : 지출결의서, draft : 기안서)
    private String appDivision;

    // 제목
    private String appTitle;

    // 내용
    private String appContent;

    // 상신일(등록일) (등록시 자동생성)
    private LocalDateTime appCreate;

    // 수정일 (수정시 자동생성)
    private LocalDateTime appUpdate;

    // 결재상태 (0 : 승인대기(기본값, 수정가능), 1 : 승인(수정불가), 2 : 반려(수정불가) )
    private int appStatus;

    // 반려사유 (반려 처리시 입력)
    private String appReason;

    // 업로드된 파일을 저장할 수 있는 객체
    private MultipartFile appContainer;

    // 원본파일명
    public String docOrigin;

    // 저장파일명
    public String docSave;

    // 첨부파일 유무(0,1)
    private int appAttach;
//    private String appNewName;

//    작성자(기안자) 이름
    private String drafterName;

//    작성자(기안자) 이메일
    private String drafterEmail;

//    결재자 이름
    private String approverName;

//    결재자 이메일
    private String approverEmail;


    public static ApprovalDto toApprovalDto(ApprovalEntity approvalEntity) {
        ApprovalDto approvalDto = new ApprovalDto();
        approvalDto.setAppId(approvalEntity.getAppId());
        approvalDto.setAppDivision(approvalEntity.getAppDivision());
        approvalDto.setAppTitle(approvalEntity.getAppTitle());
        approvalDto.setAppContent(approvalEntity.getAppContent());
        approvalDto.setAppCreate(approvalEntity.getAppCreate());
        approvalDto.setAppUpdate(approvalEntity.getAppUpdate());
        approvalDto.setAppStatus(approvalEntity.getAppStatus());
        approvalDto.setAppReason(approvalEntity.getAppReason());

        if (approvalEntity.getMemberEntity1()!=null){
//        작성자(기안자) 정보
        approvalDto.setDrafterName(approvalEntity.getMemberEntity1().getMName());
        approvalDto.setDrafterEmail(approvalEntity.getMemberEntity1().getMEmail());
        }

        if (approvalEntity.getMemberEntity2()!=null){
//        결재자 정보
        approvalDto.setApproverName(approvalEntity.getMemberEntity2().getMName());
        approvalDto.setApproverEmail(approvalEntity.getMemberEntity2().getMEmail());
        }

        if (approvalEntity.getAppAttach() == 0) {

            // 파일이 없을 경우 0
            approvalDto.setAppAttach(approvalEntity.getAppAttach());
        } else {
            // 파일이 있을 경우 1
            approvalDto.setAppAttach(approvalEntity.getAppAttach());

            // 파일 불러오기
            approvalDto.setDocOrigin(approvalEntity.getDocumentEntities().get(0).getDocOrigin());
            approvalDto.setDocSave(approvalEntity.getDocumentEntities().get(0).getDocSave());
        }

        return approvalDto;

    }

}
