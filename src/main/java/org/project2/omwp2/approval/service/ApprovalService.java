package org.project2.omwp2.approval.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.approval.repository.ApprovalRepository;
import org.project2.omwp2.document.repository.DocumentRepository;
import org.project2.omwp2.dto.ApprovalDto;
import org.project2.omwp2.entity.ApprovalEntity;
import org.project2.omwp2.entity.DocumentEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    // 결재문서 작성시
    private final ApprovalRepository approvalRepository;
    private final MemberRepository memberRepository;  // 작성자 id 조회용

    // 결재문서 파일 업로드시
    private final DocumentRepository documentRepository;



    // 결재문서 작성
    @Transactional
    public void insertApproval(ApprovalDto approvalDto, Principal principal) throws IOException{

//        작성자(기안자) 정보
        String mEmail = principal.getName();
        MemberEntity memberEntity1 = memberRepository.findBymEmail(mEmail).get();
//        결재자 정보
        String mEmail2 = approvalDto.getApproverEmail();
        MemberEntity memberEntity2 = memberRepository.findBymEmail(mEmail2).get();

        // 파일 없을 때
        if (approvalDto.getAppContainer().isEmpty()){

            ApprovalEntity approvalEntity = ApprovalEntity.toNoApprovalEntity(approvalDto, memberEntity1, memberEntity2);
            approvalRepository.save(approvalEntity);
        }
        // 파일 있을 때
        else {

            MultipartFile multipartFile = approvalDto.getAppContainer();
            // 원본 파일 이름
            String originalFilename = multipartFile.getOriginalFilename();
            // 랜덤 파일 이름
            UUID uuid = UUID.randomUUID();

            //새로운 파일 이름
            String newFilename = uuid + "_"+originalFilename;
//            String newFilename = originalFilename;
            // 파일 저장 위치
            String filePath = "C:/saveFiles/" +newFilename;

            // 파일 경로 탐색
            multipartFile.transferTo(new File(filePath));

            ApprovalEntity approvalEntity = ApprovalEntity.toYesApprovalEntity(approvalDto, memberEntity1, memberEntity2);
            Long appId = approvalRepository.save(approvalEntity).getAppId();

            Optional<ApprovalEntity> approvalEntity1 = approvalRepository.findById(appId);
            ApprovalEntity approvalEntity2 = approvalEntity1.get();

            DocumentEntity documentEntity = DocumentEntity.toDocumentEntity(approvalEntity2,originalFilename, newFilename);
            documentRepository.save(documentEntity); // 파일 저장
        }
    }


    // 결재문서 목록
    public Page<ApprovalDto> getApprovalList(Pageable pageable) {

        Page<ApprovalEntity> approvalEntityPage = approvalRepository.findAll(pageable);

        return approvalEntityPage.map(ApprovalDto::toApprovalDto);
    }

    // 결재문서 상세페이지
    public ApprovalDto findByApproval(Long id){

        Optional<ApprovalEntity> optionalApprovalEntity = approvalRepository.findById(id);

        if(optionalApprovalEntity.isPresent()) {
            return ApprovalDto.toApprovalDto(optionalApprovalEntity.get());
        }else {
            return null;
        }
    }

    // 결재서류 수정
    @Transactional
    public void updateApproval(ApprovalDto approvalDto, Principal principal)throws IOException{

        //  작성자(기안자) 정보
        String mEmail = principal.getName();
        MemberEntity memberEntity1 = memberRepository.findBymEmail(mEmail).get();
        //  결재자 정보
        String mEmail2 = approvalDto.getApproverEmail();
        MemberEntity memberEntity2 = memberRepository.findBymEmail(mEmail2).get();

        if (approvalDto.getAppContainer().isEmpty()){
            ApprovalEntity approvalEntity = ApprovalEntity.toNoUpdateApprovalEntity(approvalDto, memberEntity1, memberEntity2);
            approvalRepository.save(approvalEntity);
        }else {
            ApprovalEntity approvalEntity = ApprovalEntity.toYesUpdateApprovalEntity(approvalDto, memberEntity1, memberEntity2);
            approvalRepository.save(approvalEntity);
        }
    }

    // 결재서류 삭제
    @Transactional
    public void deleteApproval(Long appId){ approvalRepository.deleteById(appId);
    }

//    반려처리
    public ApprovalDto rejectDo(Long appId, String reason) {
        ApprovalEntity approvalEntity = approvalRepository.findById(appId).get();

        approvalEntity.setAppStatus(2);  // 승인상태 : 2 (반려)
        approvalEntity.setAppReason(reason);  // 결재자 의견

        approvalRepository.save(approvalEntity);

        return ApprovalDto.toApprovalDto(approvalEntity);
    }

//    승인처리
    public ApprovalDto approveDo(Long appId) {

        ApprovalEntity approvalEntity = approvalRepository.findById(appId).get();

        approvalEntity.setAppStatus(1);  // 승인상태 : 1 (승인)

        approvalRepository.save(approvalEntity);

        return ApprovalDto.toApprovalDto(approvalEntity);
    }
}
