package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.MemberScheduleEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberScheduleDto {

    private Long ScheduleId;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date end;
    private String scheduleDone;

    private Long mId;

    public static MemberScheduleDto toMemberScheduleDto(MemberScheduleEntity memberScheduleEntity){

        MemberScheduleDto memberScheduleDto = new MemberScheduleDto();

        memberScheduleDto.setScheduleId(memberScheduleEntity.getScheduleId());
        memberScheduleDto.setContent(memberScheduleEntity.getContent());
        memberScheduleDto.setStart(memberScheduleEntity.getStart());
        memberScheduleDto.setEnd(memberScheduleEntity.getEnd());
        memberScheduleDto.setScheduleDone(memberScheduleEntity.getScheduleDone());
        memberScheduleDto.setMId(memberScheduleEntity.getMemberEntity().getMId());

        return memberScheduleDto;
    }

}
