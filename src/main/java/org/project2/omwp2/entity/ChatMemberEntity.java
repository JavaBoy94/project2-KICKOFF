package org.project2.omwp2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "chatmember")
public class ChatMemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "c_id")
  private long cId;//아이디

  @Column(name = "c_name", nullable = false)
  private String cName; // 이름

  @Column(name = "c_email", nullable = false, unique = true)
  private String cEmail; //이메일

  @Column(name = "c_phone", nullable = false)
  private String cPhone;//전화번호

  @Column(name = "c_hireDate", nullable = true)
  private LocalDate cHireDate; // 날짜 시간 저장

  @Column(name = "c_addr", nullable = false)
  private String cAddr;  // 주소

  @Column(name = "c_position")
  private String cPosition; // 포지션

  @JoinColumn
  @ManyToOne
  private ChatDepartmentEntity dept;
}
