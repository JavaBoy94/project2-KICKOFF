package org.project2.omwp2.member.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.entity.ProfileEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.project2.omwp2.member.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

//    회원가입
    @Transactional
    public int memberJoin(MemberDto memberDto) throws IOException {

        if(memberDto.getProfileImg().isEmpty()){
//            파일이 없을때
            Long id = memberRepository.save(MemberEntity.toMemberEntity(memberDto,passwordEncoder)).getMId();

            if (memberRepository.findById(id).isEmpty()){
                return 0;
            } else {
                return 1;
            }

        } else {
//            파일이 있을때
            
//            1. 실제 파일 저장

            MultipartFile multipartFile = memberDto.getProfileImg();
            String originName = multipartFile.getOriginalFilename();  // 원본파일명
            UUID uuid = UUID.randomUUID();  // 랜덤파일명 생성

            String saveName = uuid+"_"+originName;  // 저장파일명
            String filePath = "C:/saveFiles/"+saveName;  // 파일저장경로

            multipartFile.transferTo(new File(filePath));  // 해당 경로에 저장
            
//            2. DB에 파일 정보 저장 (회원정보 저장 후, 그 id를 받아서 file entity에도 저장)

            Long id = memberRepository.save(MemberEntity.toMemberEntity(memberDto,passwordEncoder)).getMId();
            MemberEntity memberEntity = memberRepository.findById(id).get();

            Long profileId = profileRepository.save(ProfileEntity.toProfileEntity(memberEntity,originName,saveName)).getProfileId();

            if(profileRepository.findById(profileId).isEmpty()){
                return 0;
            } else {
                return 1;
            }

        }

    }

    public MemberDto getMemberDetail(String mEmail) {

        MemberEntity memberEntity = memberRepository.findBymEmail(mEmail).get();

        if(memberEntity==null){
            System.out.println("======== memberDetail fail ========");
            return null;
        }

        return MemberDto.toMemberDto(memberEntity);
    }

    @Transactional
    public int memberUpdate(MemberDto memberDto) throws IOException {

        if(memberDto.getProfileImg().isEmpty()) {
//            수정할 파일이 없을 때 => 기존 이미지 사용

            memberRepository.save(MemberEntity.toMemberEntity2(memberDto,passwordEncoder));

        } else {
//            수정할 파일이 있을 때 => 기존 파일 삭제 후, 수정할 파일 저장
//            1. 기존 파일 삭제
           ProfileEntity profileEntity =
                   profileRepository.findAllByMId(memberDto.getMId());
           
           if(profileEntity!=null){
//              기존에 등록된 이미지가 존재할 경우 삭제
               profileRepository.delete(profileEntity);
           }

//               기존 파일 삭제 성공하면 다시 파일 추가
            MultipartFile multipartFile = memberDto.getProfileImg();
            String originName = multipartFile.getOriginalFilename();  // 원본파일명
            UUID uuid = UUID.randomUUID();  // 랜덤파일명 생성

            String saveName = uuid+"_"+originName;  // 저장파일명
            String filePath = "C:/saveFiles/"+saveName;  // 파일저장경로

            multipartFile.transferTo(new File(filePath));  // 해당 경로에 저장

//            2. DB에 파일 정보 저장 (회원정보 저장 후, 그 id를 받아서 file entity에도 저장)

            Long id = memberRepository.save(MemberEntity.toMemberEntity2(memberDto,passwordEncoder)).getMId();
            MemberEntity memberEntity = memberRepository.findById(id).get();

            Long profileId = profileRepository.save(ProfileEntity.toProfileEntity(memberEntity,originName,saveName)).getProfileId();

            if(profileRepository.findById(profileId).isEmpty()){
                return 0;
            }
        }
            return 1;
    }

    public int memberDeleteDo(String mEmail) {

        MemberEntity memberEntity =
                memberRepository.findBymEmail(mEmail).get();

        memberRepository.delete(memberEntity);

        if(memberRepository.findById(memberEntity.getMId()).isEmpty()){
            System.out.println("member delete Success !");
            return 1;
        } else {
            System.out.println("member delete fail !");
            return 0;
        }
    }

    public Page<MemberDto> getMemberList(Pageable pageable) {

        Page<MemberEntity> memberEntityPage = memberRepository.findAll(pageable);

        return memberEntityPage.map(MemberDto::toMemberDto);

    }

    public MemberDto getMemberDetail2(Long id) {

       if(memberRepository.findById(id).isEmpty()){
//           해당 회원정보가 없으면 null
           return null;
       }

       return MemberDto.toMemberDto(memberRepository.findById(id).get());

    }

    public int memberDeleteDo2(Long id) {

        MemberEntity memberEntity =
                memberRepository.findById(id).get();

        memberRepository.delete(memberEntity);

        if(memberRepository.findById(memberEntity.getMId()).isEmpty()){
            System.out.println("member delete Success !");
            return 1;
        } else {
            System.out.println("member delete fail !");
            return 0;
        }
    }

    // 포지션별 페이징
    public Page<MemberDto> getMemberPositionList(String mPosition, Pageable pageable) {

        Page<MemberEntity> memberEntityList = memberRepository.findAllBymPosition(mPosition,pageable);
        Page<MemberDto> memberDtoList = memberEntityList.map(MemberDto::toMemberDto);

        return memberDtoList;

    }


    // 포지션별 목록가져오기(MULTI)
    public List<MemberDto> MultiMemberList(String mPosition) {

        List<MemberEntity> MultiMemberEntityList = memberRepository.findBymPositionDesc(mPosition);
        List<MemberDto> MultiMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : MultiMemberEntityList){
            MultiMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return MultiMemberDtoList;
    }

    // 포지션별 목록가져오기(ST)
    public List<MemberDto> StMemberList(String mPosition) {
        List<MemberEntity> StMemberEntityList = memberRepository.findBymPositionDesc(mPosition);
        List<MemberDto> StMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : StMemberEntityList){
            StMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return StMemberDtoList;
    }

    // 포지션별 목록가져오기(MF)
    public List<MemberDto> MfMemberList(String mPosition) {
        List<MemberEntity> MfMemberEntityList = memberRepository.findBymPositionDesc(mPosition);
        List<MemberDto> MfMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : MfMemberEntityList){
            MfMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return MfMemberDtoList;
    }

    // 포지션별 목록가져오기(DF)
    public List<MemberDto> DfMemberList(String mPosition) {
        List<MemberEntity> DfMemberEntityList = memberRepository.findBymPositionDesc(mPosition);
        List<MemberDto> DfMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : DfMemberEntityList){
            DfMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return DfMemberDtoList;
    }

    // 포지션별 목록가져오기(GK)
    public List<MemberDto> GkMemberList(String mPosition) {
        List<MemberEntity> GkMemberEntityList = memberRepository.findBymPositionDesc(mPosition);
        List<MemberDto> GkMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : GkMemberEntityList){
            GkMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return GkMemberDtoList;
    }

    // 회원구분별 페이징
    public Page<MemberDto> getMemberDeptList(String mDept, Pageable pageable) {

        Page<MemberEntity> memberEntityList = memberRepository.findAllBymDept(mDept,pageable);
        Page<MemberDto> memberDtoList = memberEntityList.map(MemberDto::toMemberDto);

        return memberDtoList;
    }

    // 회원구분별 목록가져오기(CP) - 회장
    public List<MemberDto> CpMemberList(String mDept) {

        List<MemberEntity> CpMemberEntityList = memberRepository.findBymDeptDesc(mDept);
        List<MemberDto> CpMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : CpMemberEntityList){
            CpMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return CpMemberDtoList;
    }

    // 회원구분별 목록가져오기(VP) - 부회장
    public List<MemberDto> VpMemberList(String mDept) {

        List<MemberEntity> VpMemberEntityList = memberRepository.findBymDeptDesc(mDept);
        List<MemberDto> VpMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : VpMemberEntityList){
            VpMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return VpMemberDtoList;
    }

    // 회원구분별 목록가져오기(GA) - 총무
    public List<MemberDto> GaMemberList(String mDept) {

        List<MemberEntity> GaMemberEntityList = memberRepository.findBymDeptDesc(mDept);
        List<MemberDto> GaMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : GaMemberEntityList){
            GaMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return GaMemberDtoList;
    }

    // 회원구분별 목록가져오기(MANAGER) - 매니저
    public List<MemberDto> ManagerMemberList(String mDept) {

        List<MemberEntity> ManagerMemberEntityList = memberRepository.findBymDeptDesc(mDept);
        List<MemberDto> ManagerMemberDtoList = new ArrayList<>();

        for(MemberEntity memberEntity : ManagerMemberEntityList){
            ManagerMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
        }

        return ManagerMemberDtoList;
    }

//    관리자메뉴 내 회원목록 검색 - 이름 기준
    public Page<MemberDto> findMemberName(String search, Pageable pageable) {
        
        Page<MemberEntity> memberEntityPage = memberRepository.findBymNameContaining(search,pageable);

        if (memberEntityPage.isEmpty()){
            return null;
        }

        Page<MemberDto> memberDtoPage = memberEntityPage.map(MemberDto::toMemberDto);

        return memberDtoPage;
    }

    //    관리자메뉴 내 회원목록 검색 - 이메일 기준
    public Page<MemberDto> findMemberEmail(String search, Pageable pageable) {

        Page<MemberEntity> memberEntityPage = memberRepository.findBymEmailContaining(search,pageable);

        if (memberEntityPage.isEmpty()){
            return null;
        }

        Page<MemberDto> memberDtoPage = memberEntityPage.map(MemberDto::toMemberDto);

        return memberDtoPage;
    }

    //    관리자메뉴 내 회원목록 검색 - 연락처 기준
    public Page<MemberDto> findMemberTel(String search, Pageable pageable) {

        Page<MemberEntity> memberEntityPage = memberRepository.findBymTelContaining(search,pageable);

        if (memberEntityPage.isEmpty()){
            return null;
        }

        Page<MemberDto> memberDtoPage = memberEntityPage.map(MemberDto::toMemberDto);

        return memberDtoPage;
    }

    // 회원구분별 목록가져오기(MEMBER) - 일반회원, 기본
//    public List<MemberDto> MemberMemberList(String mDept) {
//
//        List<MemberEntity> MemberMemberEntityList = memberRepository.findBymDeptDesc(mDept);
//        List<MemberDto> MemberMemberDtoList = new ArrayList<>();
//
//        for(MemberEntity memberEntity : MemberMemberEntityList){
//            MemberMemberDtoList.add(MemberDto.toMemberDto(memberEntity));
//        }
//
//        return MemberMemberDtoList;
//    }


}
