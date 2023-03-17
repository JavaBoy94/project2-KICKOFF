package org.project2.omwp2.dto;

import lombok.*;

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

}
