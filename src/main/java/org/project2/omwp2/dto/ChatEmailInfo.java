package org.project2.omwp2.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatEmailInfo {
  // 회원구분, 이름, 이메일
  private String deptName;
  private String memberName;
  private String email;
}
