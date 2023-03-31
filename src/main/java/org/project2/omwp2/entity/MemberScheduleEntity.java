package org.project2.omwp2.entity;

import lombok.*;
import org.project2.omwp2.dto.MemberScheduleDto;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "memberSchedule")
public class MemberScheduleEntity {

    //스케줄 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long ScheduleId;

    //스케줄 내용
    @Column(nullable = false,unique = true)
    private String content;

    //스케줄 시작일
    @Column(nullable = false)
    private Date start;

    //스케줄 종료일
    @Column(nullable = false)
    private Date end;

    //스케줄 완료여부 - 미완료 : N(기본값), 완료 : Y
    @Column(name = "schedule_done",nullable = false)
    private String scheduleDone;

    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private MemberEntity memberEntity;

    public static MemberScheduleEntity toMemberScheduleEntity(MemberScheduleDto memberScheduleDto){

        MemberScheduleEntity memberScheduleEntity = new MemberScheduleEntity();

        memberScheduleEntity.setScheduleId(memberScheduleDto.getScheduleId());
        memberScheduleEntity.setContent(memberScheduleDto.getContent());
        memberScheduleEntity.setStart(memberScheduleDto.getStart());
        memberScheduleEntity.setEnd(memberScheduleDto.getEnd());
        memberScheduleEntity.setScheduleDone(memberScheduleDto.getScheduleDone());

        return memberScheduleEntity;
    }

}
