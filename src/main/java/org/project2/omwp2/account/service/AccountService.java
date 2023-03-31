package org.project2.omwp2.account.service;

import lombok.RequiredArgsConstructor;
import org.project2.omwp2.account.repository.AccountRepository;
import org.project2.omwp2.dto.AccountDto;
import org.project2.omwp2.entity.AccountEntity;
import org.project2.omwp2.entity.MemberEntity;
import org.project2.omwp2.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
                .acTotalIncome(accountDto.getAcTotalIncome())
                .acTotalExpend(accountDto.getAcTotalExpend())
                .acTotalPay(accountDto.getAcTotalPay())
                .build();

        accountRepository.save(accountEntity);

    }

    // 게시글 목록
    public Page<AccountDto> accountList(Pageable pageable) {
        Page<AccountEntity> accountEntityPage = accountRepository.findAll(pageable);

        Page<AccountDto> accountDtoPage = accountEntityPage.map(AccountDto::toAccountDto);

        for(AccountDto accountDto :accountDtoPage) {
            accountDto.setBalance(accountRepository.findByAcId(accountDto.getAcId()));
        }

        return accountDtoPage;
    }

    // 제목으로 검색
    public Page<AccountDto> findTitle(String search, Pageable pageable) {

        Page<AccountEntity> accountEntityPage = accountRepository.findByAcTitleContaining(search,pageable);

        Page<AccountDto> accountDtoPage = accountEntityPage.map(AccountDto::toAccountDto);

        for(AccountDto accountDto :accountDtoPage) {
            accountDto.setBalance(accountRepository.findByAcId(accountDto.getAcId()));
        }

        return accountDtoPage;
    }

    // 내용으로 검색
    public Page<AccountDto> findContent(String search, Pageable pageable) {
        Page<AccountEntity> accountEntityPage = accountRepository.findByAcContentContaining(search,pageable);

        Page<AccountDto> accountDtoPage = accountEntityPage.map(AccountDto::toAccountDto);

        for(AccountDto accountDto :accountDtoPage) {
            accountDto.setBalance(accountRepository.findByAcId(accountDto.getAcId()));
        }

        return accountDtoPage;
    }

    // 제목 또는 내용으로 검색
/*    public Page<AccountDto> findTitleAndFindContent(String search, Pageable pageable) {
        Page<AccountEntity> accountEntityPage = accountRepository.findByAcTitleAndAcContent(search, pageable);

        Page<AccountDto> accountDtoPage = accountEntityPage.map(AccountDto::toAccountDto);

        for(AccountDto accountDto :accountDtoPage) {
            accountDto.setBalance(accountRepository.findByAcId(accountDto.getAcId()));
        }

        return accountDtoPage;
    }*/

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
    public int accountUpdateOk(AccountDto accountDto, String mEmail) {

        MemberEntity memberEntity = memberRepository.findBymEmail(mEmail).get();

        Long id = accountRepository.save(AccountEntity.toAccountEntity(accountDto, memberEntity)).getAcId();

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

}
