package org.project2.omwp2.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatPhoneInfo {
  // 회원구분, 이름, 전화번호
  private String deptName;
  private String memberName;
  private String phone;
}
