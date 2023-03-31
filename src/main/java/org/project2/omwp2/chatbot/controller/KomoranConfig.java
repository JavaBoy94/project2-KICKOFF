package org.project2.omwp2.chatbot.controller;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.project2.omwp2.chatbot.repository.ChatMemberRepository;
import org.project2.omwp2.chatbot.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class KomoranConfig {

//  사용자 사전 사용
//  문장 내에서 사용자 사전에 포함된 단어가 출현하면 사용자 사전에 정의된 품사를 우선적으로 갖게 됩니다.
//  주로 사람이름, 영화제목, 브랜드명, 지명 등과 같이 고유명사를 인식하는데 활용할 수 있습니다.
//  기분석 사전보다 우선 순위가 낮습니다.
//  //KOMORAN에서 기본으로 제공되는 LIGHT 모델 사용
//  Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
////사용자 사전 적용. 원하는 위치에 사용자 사전 파일을 생성한 후 경로만 지정해주면 됩니다.
//komoran.setUserDic("user_data/dic.user")

  //.dic : 각종 사전(Dictionary) 파일
  //private String DEPT_DIC="dept.dic";
  //private String DIC_DIR="static/files/";
  private String USER_DIC="user.dic";

  // DB의 부서명, 사원명
  @Autowired
  DepartmentRepository departmentRepository;//부서

  @Autowired
  ChatMemberRepository memberRepository;// 회원

  @Bean
  Komoran komoran() {
    userDic();
    Komoran komoran=new Komoran(DEFAULT_MODEL.LIGHT);
    komoran.setUserDic(USER_DIC);  // 사용자 사전
    return komoran;
  }

  //부서테이블(회원구분명), 멤버테이블(이름)
  private void userDic() {
    Set<String> keys = new HashSet<>();
    //기존에 수동으로 등록된 파일에서 고유명사만 추출
    try {
      File file=new File(USER_DIC);
      if(file.exists()) {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String data = null;
        while ((data = br.readLine()) != null) {
          if (data.startsWith("#"))//주석라인제거
            continue;
          String[] str = data.split("\\t");
          keys.add(str[0]);
        }
        br.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    //회원구분명을 set에 저장
    departmentRepository.findAll().forEach(e -> {
      keys.add(e.getDname());
      // CP(회장)			NNP
      // VP(부회장)			NNP
      // GA(총무)			NNP
      // MANAGER(매니저)		NNP
      // MEMBER(일반회원)	NNP
    });

    //사원명을 set에 저장
    memberRepository.findAll().forEach(e -> {
      keys.add(e.getCName());
      // 이이름	NNP
      // 김이름	NNP
    });

    //저장된 명단을 고유명사로 파일에 등록
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DIC));
      keys.forEach(key -> {
        try {
          bw.write(key + "\tNNP\n");
          System.out.println(key);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
      bw.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }
}
