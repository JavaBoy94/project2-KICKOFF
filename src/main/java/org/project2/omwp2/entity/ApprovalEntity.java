package org.project2.omwp2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.dto.ApprovalDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "approval")
public class ApprovalEntity {

    // 문서 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //문서 추가시 자동 1씩 증가
    @Column(name = "app_id")
    private Long appId;

    // 구분 (expend : 지출결의서, draft : 기안서)
    @Column(name = "app_division")
    private String appDivision;

    // 제목
    @Column(name = "app_title", nullable = false)
    private String appTitle;

    // 내용
    @Column(name = "app_content", nullable = false)
    private String appContent;

    // 상신일(등록일) (등록시 자동생성)
    @CreationTimestamp
    @Column(name = "app_create", updatable = false)
    private LocalDateTime appCreate;

    // 수정일 (수정시 자동생성)
    @UpdateTimestamp
    @Column(name = "app_update", insertable = false)
    private LocalDateTime appUpdate;

    // 결재상태 (0 : 승인대기(기본값, 수정가능), 1 : 승인(수정불가), 2 : 반려(수정불가) )
    @Column(name = "app_status")
    private int appStatus;

    // 반려사유 (반려 처리시 입력)
    @Column(name = "app_reason")
    private String appReason;

    // 첨부파일 유무(0,1)
    @Column(name = "app_attach",nullable = false)
    private int appAttach;

    // 기안자 ID (매니저 이상만 기안 가능, 일반회원 불가)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drafter_id")
    private MemberEntity memberEntity1;

    // 결재자 ID
    // 결재자는 기안자보다 회원구분이 높은 사람만
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private MemberEntity memberEntity2;

    // 첨부파일
    @OneToMany(mappedBy = "approvalEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<DocumentEntity> documentEntities  =new ArrayList<>();

    // ↓
    // 결재문서 작성
    public static ApprovalEntity toNoApprovalEntity(ApprovalDto approvalDto,
                                                    MemberEntity memberEntity1, MemberEntity memberEntity2){
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setAppDivision(approvalDto.getAppDivision());
        approvalEntity.setAppTitle(approvalDto.getAppTitle());
        approvalEntity.setAppContent(approvalDto.getAppContent());
        approvalEntity.setAppStatus(approvalDto.getAppStatus());
        approvalEntity.setAppReason(approvalDto.getAppReason());
        approvalEntity.setAppAttach(0); //첨부 파일 없을 때
        approvalEntity.setMemberEntity1(memberEntity1);  // 작성자(기안자)
        approvalEntity.setMemberEntity2(memberEntity2);  // 결재자
        return approvalEntity;
    }

    public static ApprovalEntity toYesApprovalEntity(ApprovalDto approvalDto,
                                                     MemberEntity memberEntity1, MemberEntity memberEntity2){
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setAppDivision(approvalDto.getAppDivision());
        approvalEntity.setAppTitle(approvalDto.getAppTitle());
        approvalEntity.setAppContent(approvalDto.getAppContent());
        approvalEntity.setAppStatus(approvalDto.getAppStatus());
        approvalEntity.setAppReason(approvalDto.getAppReason());
        approvalEntity.setAppAttach(1); //첨부 파일 있을 때
        approvalEntity.setMemberEntity1(memberEntity1);  // 작성자(기안자)
        approvalEntity.setMemberEntity2(memberEntity2);  // 결재자
        return approvalEntity;
    }

    // ↓
    // 결재문서 수정
    public static ApprovalEntity toNoUpdateApprovalEntity(ApprovalDto approvalDto,
                                                          MemberEntity memberEntity1, MemberEntity memberEntity2){
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setAppId(approvalDto.getAppId());
        approvalEntity.setAppDivision(approvalDto.getAppDivision());
        approvalEntity.setAppTitle(approvalDto.getAppTitle());
        approvalEntity.setAppContent(approvalDto.getAppContent());
        approvalEntity.setAppStatus(approvalDto.getAppStatus());
        approvalEntity.setAppReason(approvalDto.getAppReason());
        approvalEntity.setAppAttach(0); //첨부 파일 없을 때
        approvalEntity.setMemberEntity1(memberEntity1);  // 작성자(기안자)
        approvalEntity.setMemberEntity2(memberEntity2);  // 결재자
        return approvalEntity;
    }

    public static ApprovalEntity toYesUpdateApprovalEntity(ApprovalDto approvalDto,
                                                           MemberEntity memberEntity1, MemberEntity memberEntity2){
        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setAppId(approvalDto.getAppId());
        approvalEntity.setAppDivision(approvalDto.getAppDivision());
        approvalEntity.setAppTitle(approvalDto.getAppTitle());
        approvalEntity.setAppContent(approvalDto.getAppContent());
        approvalEntity.setAppStatus(approvalDto.getAppStatus());
        approvalEntity.setAppReason(approvalDto.getAppReason());
        approvalEntity.setAppAttach(1); //첨부 파일 있을 때
        approvalEntity.setMemberEntity1(memberEntity1);  // 작성자(기안자)
        approvalEntity.setMemberEntity2(memberEntity2);  // 결재자
        return approvalEntity;
    }
}
