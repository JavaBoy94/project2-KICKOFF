package org.project2.omwp2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.project2.omwp2.dto.ChatAnswerDto;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class ChatAnswerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long no;

  private String content;

  private String keyword;    //name

  public ChatAnswerEntity keyword(String keyword) {
    this.keyword=keyword;
    return this;
  }

  public ChatAnswerDto toAnswerDTO() {
    return ChatAnswerDto.builder()
            .no(no).content(content).keyword(keyword)
            .build();
  }
}
