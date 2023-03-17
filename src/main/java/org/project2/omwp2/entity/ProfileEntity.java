package org.project2.omwp2.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@ToString
@Entity
@Table(name="profile")
public class ProfileEntity {

//    프로필 사진 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    // 원본파일명
    @Column(name = "doc_origin",nullable = false)
    public String docOrigin;

    // 저장파일명
    @Column(name = "doc_save",nullable = false)
    public String docSave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;
}
