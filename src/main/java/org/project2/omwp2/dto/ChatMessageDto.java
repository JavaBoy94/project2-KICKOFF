package org.project2.omwp2.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatMessageDto {  // 메시지 출력

  private String today;

  private String time;

  private ChatAnswerDto answer;

  public ChatMessageDto today(String today) {
    this.today=today;
    return this;
  }
  public ChatMessageDto answer(ChatAnswerDto answer) {   // 답변  , 키워드 , 전화 번호
    this.answer=answer;
    return this;
  }
}
