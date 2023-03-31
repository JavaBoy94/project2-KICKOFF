package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.constant.Role;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDto {

    private Long mId;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다." )
    private String mEmail;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String mPw;

    private String mZipcode;
    private String mAddr1;
    private String mAddr2;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    @Pattern(regexp = "[A-Za-z0-9가-힣]{2,}", message = "닉네임 형식이 올바르지 않습니다.")
    private String mName;

    @NotBlank(message = "전화번호는 필수 입력 사항입니다")
    @Pattern(regexp = "[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}", message = "전화번호 형식이 맞지 않습니다.")
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

            System.out.println(memberEntity.getProfileEntities().get(0)+"  <<<<");
//            프로필 사진이 존재할 경우
            memberDto.setOriginName(memberEntity.getProfileEntities().get(0).getProfileOrigin());
            memberDto.setSaveName(memberEntity.getProfileEntities().get(0).getProfileSave());
        }
        return memberDto;
    }
}
