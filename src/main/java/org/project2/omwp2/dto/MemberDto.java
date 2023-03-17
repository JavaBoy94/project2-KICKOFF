package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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

}
