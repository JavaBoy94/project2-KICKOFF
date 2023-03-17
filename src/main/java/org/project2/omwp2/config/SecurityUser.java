package org.project2.omwp2.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.project2.omwp2.entity.MemberEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


@Getter
@Setter
@ToString
public class SecurityUser extends User {

    // 로그인 정보 사용자
    private MemberEntity memberEntity;

    public SecurityUser(MemberEntity memberEntity) {
        super(memberEntity.getMEmail(), memberEntity.getMPw(),
                AuthorityUtils.createAuthorityList(memberEntity.getMRole().toString()));

        this.memberEntity = memberEntity;
    }
}

