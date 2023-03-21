package org.project2.omwp2.config;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String mEmail) throws UsernameNotFoundException {
        // DB에 있는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findBymEmail(mEmail);

        if(memberEntity.isEmpty()){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }


        MemberEntity memberEntity1 = memberEntity.get();

        System.out.println(memberEntity1.getMEmail() + "<<<<<<<< email");
        System.out.println(memberEntity1.getMPw() + "<<<<<<<< pw");
        System.out.println(memberEntity1.getMName() + "<<<<<<<< name");
        System.out.println(memberEntity1.getMTel() + "<<<<<< tel");
        System.out.println(memberEntity1.getMZipcode() + "<<<<<< zipcode");
        System.out.println(memberEntity1.getMAddr1() + "<<<<<< addr1");
        System.out.println(memberEntity1.getMAttach() + "<<<<<< attch");
        System.out.println(memberEntity1.getMRole().toString() + "<<<<<<<<<<Role");
        System.out.println(memberEntity1.getMDept() + "<<<<<< dept");
        System.out.println(memberEntity1.getMPosition() + "<<<<<< position");

        return User.builder()
                .username(memberEntity1.getMEmail())
                .password(memberEntity1.getMPw())
                .roles(memberEntity1.getMRole().toString())
                .build();
    }
}
