package org.project2.omwp2.member.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.entity.ProfileEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.project2.omwp2.member.repository.ProfileRepository;
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

//    회원가입
    @Transactional
    public int memberJoin(MemberDto memberDto) throws IOException {

        if(memberDto.getProfileImg().isEmpty()){
//            파일이 없을때
            Long id = memberRepository.save(MemberEntity.toMemberEntity(memberDto)).getMId();

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

            Long id = memberRepository.save(MemberEntity.toMemberEntity(memberDto)).getMId();
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
}
