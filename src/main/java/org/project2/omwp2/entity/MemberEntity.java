package org.project2.omwp2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.project2.omwp2.dto.MemberDto;
import org.project2.omwp2.member.constant.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Builder
@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_id")
    private Long mId;

//    이메일
    @Column(name = "m_email",nullable = false, unique = true)
    private String mEmail;

//    비밀번호
    @Column(name = "m_pw", nullable = false)
    private String mPw;

//    우편번호
    @Column(name = "m_zipcode", nullable = false)
    private String mZipcode;

//    주소
    @Column(name = "m_addr1", nullable = false)
    private String mAddr1;

//    상세주소(선택)
    @Column(name = "m_addr2")
    private String mAddr2;

//    이름
    @Column(name = "m_name", nullable = false)
    private String mName;

//    전화번호
    @Column(name = "m_tel", nullable = false)
    private String mTel;

//    자기소개
    @Column(name = "m_intro")
    private String mIntro;

//    생성일
    @CreationTimestamp
    @Column(name = "m_create",updatable = false)
    private LocalDateTime mCreate;

//  권한 - MEMBER(일반회원), ADMIN(관리자), BLACK(정지된 회원)
    @Enumerated(EnumType.STRING)
    private Role mRole;

//    회원구분 - MEMBER(일반회원, 기본), MANAGER(매니저), GA(총무), VP(부회장), CP(회장)
    @Column(name = "m_dept",nullable = false)
    private String mDept;

//    희망포지션(선택) - MULTI(상관없음), ST(공격수), MF(미드필더), DF(수비수), GK(골키퍼)
    @Column(name = "m_position")
    private String mPosition;

// 프로필 사진 유무 (1 : o , 0 : x)
    @Column(name = "m_attach")
    private int mAttach;

// 프로필 사진
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProfileEntity> profileEntities =new ArrayList<>();
// 회계내역
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AccountEntity> accountEntities = new ArrayList<>();
// 결재1 - 기안자
    @OneToMany(mappedBy = "memberEntity1",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<ApprovalEntity> approvalEntities1 = new ArrayList<>();
// 결재2 - 결재자
    @OneToMany(mappedBy = "memberEntity2",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<ApprovalEntity> approvalEntities2 = new ArrayList<>();

    //    공지사항 게시판
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<NoticeEntity> noticeEntities = new ArrayList<>();

    //   커뮤니티 게시판
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<BoardEntity> boardEntities = new ArrayList<>();

//  댓글
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<CommentEntity> commentEntities = new ArrayList<>();


//    회원가입용
    public static MemberEntity toMemberEntity(MemberDto memberDto, PasswordEncoder passwordEncoder) {

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMEmail(memberDto.getMEmail());
        memberEntity.setMPw(passwordEncoder.encode(memberDto.getMPw()));
        memberEntity.setMName(memberDto.getMName());
        memberEntity.setMZipcode(memberDto.getMZipcode());
        memberEntity.setMAddr1(memberDto.getMAddr1());
        memberEntity.setMAddr2(memberDto.getMAddr2());
        memberEntity.setMTel(memberDto.getMTel());
        memberEntity.setMIntro(memberDto.getMIntro());
        memberEntity.setMRole(Role.MEMBER);
        memberEntity.setMDept("MEMBER");
        memberEntity.setMPosition(memberDto.getMPosition());
        if(memberDto.getProfileImg().isEmpty()){
            memberEntity.setMAttach(0);
        } else {
            memberEntity.setMAttach(1);
        }

        return memberEntity;
    }

//    회원수정용
public static MemberEntity toMemberEntity2(MemberDto memberDto, PasswordEncoder passwordEncoder) {

    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setMId(memberDto.getMId());
    memberEntity.setMEmail(memberDto.getMEmail());
    memberEntity.setMPw(passwordEncoder.encode(memberDto.getMPw()));
    memberEntity.setMName(memberDto.getMName());
    memberEntity.setMZipcode(memberDto.getMZipcode());
    memberEntity.setMAddr1(memberDto.getMAddr1());
    memberEntity.setMAddr2(memberDto.getMAddr2());
    memberEntity.setMTel(memberDto.getMTel());
    memberEntity.setMIntro(memberDto.getMIntro());
    memberEntity.setMRole(memberDto.getMRole());
    memberEntity.setMDept(memberDto.getMDept());
    memberEntity.setMCreate(memberDto.getMCreate());
    memberEntity.setMPosition(memberDto.getMPosition());
    memberEntity.setMAttach(1);
//    memberEntity.setMAttach(memberDto.getMAttach());
//    if(memberDto.getProfileImg().isEmpty()){
//        memberEntity.setMAttach(0);
//    } else {
//        memberEntity.setMAttach(1);
//    }

    return memberEntity;
}
}
