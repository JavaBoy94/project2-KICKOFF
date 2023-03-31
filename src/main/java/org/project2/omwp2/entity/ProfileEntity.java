package org.project2.omwp2.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
//@ToString
@Entity
@Table(name="profile")
public class ProfileEntity {

//    프로필 사진 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    // 원본파일명
    @Column(name = "profile_origin",nullable = false)
    public String profileOrigin;

    // 저장파일명
    @Column(name = "profile_save",nullable = false)
    public String profileSave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;

    public static ProfileEntity toProfileEntity(MemberEntity memberEntity,
                                         String originName,
                                         String saveName) {

        ProfileEntity profileEntity = new ProfileEntity();

        profileEntity.setMemberEntity(memberEntity);
        profileEntity.setProfileOrigin(originName);
        profileEntity.setProfileSave(saveName);

        return profileEntity;
    }
}
