package org.project2.omwp2.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatAddressInfo {
  // 회원구분, 이름, 주소
  private String deptName;
  private String memberName;
  private String address;
}
