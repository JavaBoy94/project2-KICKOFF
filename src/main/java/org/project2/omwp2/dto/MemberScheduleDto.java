package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.MemberScheduleEntity;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberScheduleDto {

    private Long ScheduleId;
    private String ScheduleBoard;
    private LocalDateTime ScheduleStart;
    private LocalDateTime ScheduleEnd;
    private String scheduleDone;

    private Long mId;

    public static MemberScheduleDto toMemberScheduleDto(MemberScheduleEntity memberScheduleEntity){

        MemberScheduleDto memberScheduleDto = new MemberScheduleDto();

        memberScheduleDto.setScheduleId(memberScheduleEntity.getScheduleId());
        memberScheduleDto.setScheduleBoard(memberScheduleEntity.getScheduleBoard());
        memberScheduleDto.setScheduleStart(memberScheduleEntity.getScheduleStart());
        memberScheduleDto.setScheduleEnd(memberScheduleEntity.getScheduleEnd());
        memberScheduleDto.setScheduleDone(memberScheduleEntity.getScheduleDone());
        memberScheduleDto.setMId(memberScheduleEntity.getMemberEntity().getMId());

        return memberScheduleDto;
    }

}
