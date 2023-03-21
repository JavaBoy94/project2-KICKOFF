package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.constant.Role;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDto {

    private Long mId;
    private String mEmail;
    private String mPw;
    private String mZipcode;
    private String mAddr1;
    private String mAddr2;
    private String mName;
    private String mTel;
    private String mIntro;
    private LocalDateTime mCreate;
    private Role mRole;
    private String mDept;
    private String mPosition;
    private int mAttach;
    private MultipartFile profileImg;

    private String originName;
    private String saveName;

    public static MemberDto toMemberDto(MemberEntity memberEntity) {

        MemberDto memberDto = new MemberDto();
        memberDto.setMId(memberEntity.getMId());
        memberDto.setMEmail(memberEntity.getMEmail());
        memberDto.setMPw(memberEntity.getMPw());
        memberDto.setMName(memberEntity.getMName());
        memberDto.setMTel(memberEntity.getMTel());
        memberDto.setMZipcode(memberEntity.getMZipcode());
        memberDto.setMAddr1(memberEntity.getMAddr1());
        memberDto.setMAddr2(memberEntity.getMAddr2());
        memberDto.setMCreate(memberEntity.getMCreate());
        memberDto.setMRole(memberEntity.getMRole());
        memberDto.setMDept(memberEntity.getMDept());
        memberDto.setMPosition(memberEntity.getMPosition());
        memberDto.setMIntro(memberEntity.getMIntro());
        memberDto.setMAttach(memberEntity.getMAttach());
        if(memberEntity.getMAttach() == 1) {
//            프로필 사진이 존재할 경우
            memberDto.setOriginName(memberEntity.getProfileEntities().get(0).getProfileOrigin());
            memberDto.setSaveName(memberEntity.getProfileEntities().get(0).getProfileSave());
        }
        return memberDto;
    }
}
