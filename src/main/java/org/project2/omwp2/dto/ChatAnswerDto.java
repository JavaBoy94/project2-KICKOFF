package org.project2.omwp2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatAnswerDto {

  private long no;
  private String content;
  private String keyword; // 키워드
  private ChatPhoneInfo phone;  // 전화번호 정보
  private ChatAddressInfo address; // 주소 정보
  private ChatPositionInfo position; // 포지션 정보
  private ChatEmailInfo email; // 이메일 정보

  public ChatAnswerDto phone(ChatPhoneInfo phone){
    this.phone=phone;
    return this;
  }

  public ChatAnswerDto address(ChatAddressInfo address){
    this.address=address;
    return this;
  }

  public ChatAnswerDto position(ChatPositionInfo position){
    this.position=position;
    return this;
  }

  public ChatAnswerDto email(ChatEmailInfo email){
    this.email=email;
    return this;
  }

}
