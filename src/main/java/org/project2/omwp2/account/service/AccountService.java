package org.project2.omwp2.account.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.account.repository.AccountRepository;
import org.project2.omwp2.dto.AccountDto;
import org.project2.omwp2.entity.AccountEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;

    // 게시글 등록
    public void accountInsert(AccountDto accountDto, String mEmail) {
        // 현재 로그인한 회원 id 찾기
        MemberEntity memberEntity = memberRepository.findBymEmail(mEmail).get();

        AccountEntity accountEntity = AccountEntity.builder()
                .memberEntity(memberEntity)
                .acTitle(accountDto.getAcTitle())
                .acSurplus(accountDto.getAcSurplus())
                .acContent(accountDto.getAcContent())
                .acIncome(accountDto.getAcIncome())
                .acExpend(accountDto.getAcExpend())
                .build();

        accountRepository.save(accountEntity);
    }

    // 게시글 목록
    public List<AccountDto> accountList() {
        List<AccountDto> accountDtoList = new ArrayList<>();
        // DB를 가져온다
        List<AccountEntity> accountEntityList = accountRepository.findAll();
        for(AccountEntity accountEntity : accountEntityList){
            accountDtoList.add(AccountDto.toAccountDto(accountEntity));
        }
        return accountDtoList;
    }

    // 게시글 상세페이지
    public AccountDto accountDetail(Long acId) {
        Optional<AccountEntity> accountEntity = accountRepository.findById(acId);

        if(accountEntity.isPresent()){
            return AccountDto.toAccountDto(accountEntity.get());
        }else{
            return null;
        }
    }

    // 게시글 수정 페이지 이동
    public AccountDto accountUpdate(Long acId) {
        Optional<AccountEntity> accountEntity = accountRepository.findById(acId);

        if(accountEntity.isPresent()){
            return AccountDto.toAccountDto(accountEntity.get());
        }else{
            return null;
        }
    }

    // 게시글 수정 실행
    @Transactional
    public int accountUpdateOk(AccountDto accountDto) {
        Long id = accountRepository.save(AccountEntity.toAccountEntity(accountDto)).getAcId();

        if (id == null) {
            return 0;
        }
        return 1;
    }

    // 게시글 삭제
    @Transactional
    public int accountDelete(Long acId) {
        AccountEntity accountEntity = accountRepository.findById(acId).get();

        accountRepository.delete(accountEntity);
        return 1;
    }

    // 게시글 삭제

}
