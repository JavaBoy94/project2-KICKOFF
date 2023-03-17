package org.project2.omwp2.member.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.entity.ProfileEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public int memberJoin(MemberDto memberDto) {

        if(memberDto.getProfileImg().isEmpty()){
//            프로필 사진이 없을 때
            
        }
    }
}
