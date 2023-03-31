package org.project2.omwp2.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatPositionInfo {
  // 회원구분, 이름, 포지션
  private String deptName;
  private String memberName;
  private String position;
}
