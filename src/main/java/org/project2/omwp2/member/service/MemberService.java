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
}
