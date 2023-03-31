package org.project2.omwp2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "dept")  // 부서 테이블
@Entity
public class ChatDepartmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "d_no")
  private long dno;  // 부서 번호

  @Column(name = "d_name")
  private String dname; // 부서명

  @Column(name = "d_depth")
  private int depth;//0,1,2

  @JoinColumn
  @ManyToOne//parent_dno  // self join
  private ChatDepartmentEntity parent;

}