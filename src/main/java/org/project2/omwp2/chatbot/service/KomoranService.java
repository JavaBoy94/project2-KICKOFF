package org.project2.omwp2.chatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.project2.omwp2.chatbot.repository.ChatMemberRepository;
import org.project2.omwp2.chatbot.repository.IntentionRepository;
import org.project2.omwp2.dto.*;
import org.project2.omwp2.entity.ChatAnswerEntity;
import org.project2.omwp2.entity.ChatIntentionEntity;
import org.project2.omwp2.entity.ChatMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KomoranService {

  @Autowired
  private Komoran komoran;  // 등록 Bean

  //  NLP(Natural Language Processing, 자연어 처리)는 인공지능의 한 분야로서 머신러닝을 사용하여 텍스트와 데이터를 처리하고
//  해석합니다.  자연어 인식 및 자연어 생성이 NLP의 유형입니다.
  public ChatMessageDto nlpAnalyze(String message) {

    //입력 문장에 대한 형태소 분석을 수행합니다. 입력 문장에 대한 형태소 분석 결과를 KomoranResult 객체로 반환합니다.
    KomoranResult result = komoran.analyze(message);// komoran message 분석
    //문자에서 명사들(리스트)만 추출한 목록 중복제거해서 set
    Set<String> nouns = result.getNouns().stream().collect(Collectors.toSet());
    nouns.forEach((noun) -> {
      System.out.println(">>>:" + noun);
    });
    ;//메세지에서 명사추출  noun
    return analyzeToken(nouns);
  }

  // 추출된 명사를 이용하여  DB 접근
  private ChatMessageDto analyzeToken(Set<String> nouns) {
    // 시간 설정
    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
    ChatMessageDto chatMessageDto = ChatMessageDto.builder()
            .time(today.format(timeFormatter))
            .build();

    //1차 ->  존재하는지
    for (String token : nouns) {

      Optional<ChatIntentionEntity> result = decisionTree(token, null);

      if (result.isEmpty()) continue;//존재하지 않으면 다음토큰 검색
      //1차 토근확인시 실행
      System.out.println(">>>>1차:" + token);
      //1차목록 복사
      Set<String> next = nouns.stream().collect(Collectors.toSet());
      //목록에서 1차토큰 제거
      next.remove(token);

      //2차분석 메서드
      ChatAnswerDto answer = analyzeToken(next, result).toAnswerDTO();

      // 추가 시 밑에 입력*****************************************************************************
      //전화인경우 전화,전화번호 번호탐색
      if (token.contains("전화")) {
        // 전화 키워드가 있으면 -> analyzeTokenIsPhone(next)
        ChatPhoneInfo phone = analyzeTokenIsPhone(next);
        answer.phone(phone);//전화인경우에만 전화 데이터

      }else if (token.contains("주소")) {
        // 주소 키워드가 있으면 -> analyzeTokenIsAddress(next)
        ChatAddressInfo address = analyzeTokenIsAddress(next);
        answer.address(address);//주소인경우에만 주소 데이터

      }else if (token.contains("포지션")) {
        // 포지션 키워드가 있으면 -> analyzeTokenIsPosition(next)
        ChatPositionInfo position = analyzeTokenIsPosition(next);
        answer.position(position);//주소인경우에만 주소 데이터

      }else if (token.contains("이메일")) {
        // 이메일 키워드가 있으면 -> analyzeTokenIsEmail(next)
        ChatEmailInfo email = analyzeTokenIsEmail(next);
        answer.email(email);//주소인경우에만 주소 데이터

      } else if (token.contains("안녕")) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        chatMessageDto.today(today.format(dateFormatter));//처음 접속할때만 날짜표기
      }
      chatMessageDto.answer(answer);//토큰에 대한 응답정보
      return chatMessageDto;
    }
      // 추가 시 위에 입력*****************************************************************************

    //분석 명사들이 등록한 의도와 일치하는게 존재하지 않을경우 null  "기타나 null일 경우"
    ChatAnswerDto answer = decisionTree("기타", null).get().getAnswer().toAnswerDTO();
    chatMessageDto.answer(answer);//토큰에 대한 응답정보
    return chatMessageDto;
  }

  // 1차
  @Autowired
  IntentionRepository intention;
  //의도가 존재하는지 DB에서 파악 -> 키워드 값 존재 하는 지  ->DB에 키워드가 있는지 확인
  private Optional<ChatIntentionEntity> decisionTree(String token, ChatIntentionEntity upper) {
    return intention.findByNameAndUpper(token, upper);
  }

  //1차의도가 존재하면
  //하위의도가 존재하는지 파악
  private ChatAnswerEntity analyzeToken(Set<String> next, Optional<ChatIntentionEntity> upper) {

    for (String token : next) {
      // 1차의도를 부모로하는 토큰이 존재하는지 파악
      Optional<ChatIntentionEntity> result = decisionTree(token, upper.get());

      if (result.isEmpty()) continue;

      return result.get().getAnswer();   //1차-2차 존재하는경우 답변
    }
    return upper.get().getAnswer();  //1차만 존재하는 답변
  }

  // 추가 시 밑에 입력*****************************************************************************
  //2차 - 1차의 '전화' 키워드가 있으면 이름 검색
  @Autowired
  ChatMemberRepository member;

  private ChatPhoneInfo analyzeTokenIsPhone(Set<String> next) {

    for (String cName : next) {

      Optional<ChatMemberEntity> m = member.findBycName(cName);

      if (m.isEmpty()) continue;
      //존재하면
      String deptName = m.get().getDept().getDname(); // 부서명
      String phone = m.get().getCPhone();  // 전화 번호
      String memberName = m.get().getCName();  // 이름

      return ChatPhoneInfo.builder()
              .deptName(deptName)
              .phone(phone)
              .memberName(memberName)
              .build();
    }
    return null;
  }

  //2차 - 1차의 '주소' 키워드가 있으면 이름 검색
  @Autowired
  ChatMemberRepository member2;

  private ChatAddressInfo analyzeTokenIsAddress(Set<String> next) {

    for (String cName : next) {

      Optional<ChatMemberEntity> m = member2.findBycName(cName);

      if (m.isEmpty()) continue;
      //존재하면
      String deptName = m.get().getDept().getDname(); // 부서명
      String address = m.get().getCAddr();  // 주소
      String memberName = m.get().getCName();  // 이름

      return ChatAddressInfo.builder()
              .deptName(deptName)
              .address(address)
              .memberName(memberName)
              .build();
    }
    return null;
  }

  //2차 - 1차의 '포지션' 키워드가 있으면 이름 검색
  @Autowired
  ChatMemberRepository member3;

  private ChatPositionInfo analyzeTokenIsPosition(Set<String> next) {

    for (String cName : next) {

      Optional<ChatMemberEntity> m = member3.findBycName(cName);

      if (m.isEmpty()) continue;
      //존재하면
      String deptName = m.get().getDept().getDname(); // 부서명
      String position = m.get().getCPosition();  // 포지션
      String memberName = m.get().getCName();  // 이름

      return ChatPositionInfo.builder()
              .deptName(deptName)
              .position(position)
              .memberName(memberName)
              .build();
    }
    return null;
  }

  //2차 - 1차의 '이메일' 키워드가 있으면 이름 검색
  @Autowired
  ChatMemberRepository member4;

  private ChatEmailInfo analyzeTokenIsEmail(Set<String> next) {

    for (String cName : next) {

      Optional<ChatMemberEntity> m = member4.findBycName(cName);

      if (m.isEmpty()) continue;
      //존재하면
      String deptName = m.get().getDept().getDname(); // 부서명
      String email = m.get().getCEmail();  // 이메일
      String memberName = m.get().getCName();  // 이름

      return ChatEmailInfo.builder()
              .deptName(deptName)
              .email(email)
              .memberName(memberName)
              .build();
    }
    return null;
  }
  // 추가 시 위에 입력*****************************************************************************
}
