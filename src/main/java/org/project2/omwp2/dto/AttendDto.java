package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.AttendEntity;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AttendDto {

    private Long attendId;
    private LocalDateTime attendStart;
    private LocalDateTime attendEnd;
    private Long mId;

    public static AttendDto toAttendDto(AttendEntity attendEntity){

        AttendDto attendDto = new AttendDto();

        attendDto.setAttendId(attendEntity.getAttendId());
        attendDto.setAttendStart(attendEntity.getAttendStart());
        attendDto.setAttendEnd(attendEntity.getAttendEnd());
        attendDto.setMId(attendEntity.getMemberEntity().getMId());

        return attendDto;

    }
}
