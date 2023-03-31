package org.project2.omwp2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "intention")
public class ChatIntentionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long no;
  private String name;

  @JoinColumn
  @ManyToOne
  private ChatAnswerEntity answer;

  @JoinColumn
  @ManyToOne
  private ChatIntentionEntity upper;
}
