package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project2.omwp2.account.repository.AccountRepository;
import org.project2.omwp2.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccountDto {

    @Autowired
    private static AccountRepository accountRepository;

    private Long acId;

    // 제목(title)
    @NotBlank
    private String acTitle;

    // 수입 지출 항목
    @NotBlank
    private String acContent;

    // 수입 금액
    @NotBlank
    private int acIncome;

    // 지출 금액
    @NotBlank
    private int acExpend;

    // 이월금
    private int acSurplus;

    // 총 수입 금액
    private int acTotalIncome;

    // 총 지출 금액
    private int  acTotalExpend;

    // 총액
    private int acTotalPay;

    // 게시글 등록일
    @CreationTimestamp
    private LocalDateTime acCreate;

    // 게시글 수정일
    @UpdateTimestamp
    private LocalDateTime acUpdate;

    // 작성자 이름
    private String mName;

//    작성자 id
    private Long mId;

    // 잔여금
    private int balance;


    // 변환
    public static AccountDto toAccountDto(AccountEntity accountEntity){
        AccountDto accountDto = new AccountDto();

        accountDto.setMName(accountEntity.getMemberEntity().getMName());
        accountDto.setAcId(accountEntity.getAcId());
        accountDto.setAcTitle(accountEntity.getAcTitle());
        accountDto.setAcContent(accountEntity.getAcContent());
        accountDto.setAcIncome(accountEntity.getAcIncome());
        accountDto.setAcExpend(accountEntity.getAcExpend());
        accountDto.setAcSurplus(accountEntity.getAcSurplus());
        accountDto.setAcTotalIncome(accountEntity.getAcTotalIncome());
        accountDto.setAcTotalExpend(accountEntity.getAcTotalExpend());
        accountDto.setAcTotalPay(accountEntity.getAcTotalPay());
        accountDto.setAcCreate(accountEntity.getAcCreate());
        accountDto.setAcUpdate(accountEntity.getAcUpdate());
        accountDto.setMId(accountEntity.getMemberEntity().getMId());

        return accountDto;
    }
}
