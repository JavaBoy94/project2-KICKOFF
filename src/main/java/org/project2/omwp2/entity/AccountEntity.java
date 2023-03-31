package org.project2.omwp2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.dto.AccountDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ac_id")
    private Long acId;

    // 제목
    @Column(name = "ac_title", nullable = false)
    private String acTitle;

    // 수입 지출 내역(항목)
    @Column(name = "ac_content", nullable = false)
    private String acContent;

    // 수입 금액
    @Column(name = "ac_income")
    private int acIncome;

    // 지출 금액
    @Column(name = "ac_expend")
    private int acExpend;

    // 잔여금(이월금)
    @Column(name = "ac_surplus")
    private int acSurplus;

    // 총 수입 금액
    @Column(name = "ac_total_income")
    private int acTotalIncome;

    // 총 지출 금액
    @Column(name = "ac_total_expend")
    private int  acTotalExpend;

    // 총액
    @Column(name = "ac_total_pay")
    private int acTotalPay;

    // 게시글 등록일
    @CreationTimestamp
    @Column(name = "ac_create", updatable = false)
    private LocalDateTime acCreate;

    // 게시글 수정일
    @UpdateTimestamp
    @Column(name = "ac_update", insertable = false)
    private LocalDateTime acUpdate;

    // 첨부파일 유무(0,1)
    @Column(name = "ac_attach",nullable = false)
    private int acAttach;
//    작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;
//    첨부파일
    @OneToMany(mappedBy = "accountEntity",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<DocumentEntity> documentEntities = new ArrayList<>();


    public static AccountEntity toAccountEntity(AccountDto accountDto, MemberEntity memberEntity){
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setMemberEntity(memberEntity);
        accountEntity.setAcId(accountDto.getAcId());
        accountEntity.setAcTitle(accountDto.getAcTitle());
        accountEntity.setAcContent(accountDto.getAcContent());
        accountEntity.setAcIncome(accountDto.getAcIncome());
        accountEntity.setAcExpend(accountDto.getAcExpend());
        accountEntity.setAcSurplus(accountDto.getAcSurplus());
        accountEntity.setAcTotalIncome(accountDto.getAcTotalIncome());
        accountEntity.setAcTotalExpend(accountDto.getAcTotalExpend());
        accountEntity.setAcTotalPay(accountDto.getAcTotalPay());
        accountEntity.setAcCreate(accountDto.getAcCreate());
        accountEntity.setAcUpdate(accountDto.getAcUpdate());

        return accountEntity;
    }
}
