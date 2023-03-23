package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.TeamScheduleEntity;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TeamScheduleDto {

    private Long ScheduleId;
    private String ScheduleBoard;
    private LocalDateTime ScheduleStart;
    private LocalDateTime ScheduleEnd;
    private String scheduleDone;

    private Long mId;


    public static TeamScheduleDto toTeamScheduleDto(TeamScheduleEntity teamScheduleEntity){

        TeamScheduleDto teamScheduleDto = new TeamScheduleDto();

        teamScheduleDto.setScheduleId(teamScheduleEntity.getScheduleId());
        teamScheduleDto.setScheduleBoard(teamScheduleEntity.getScheduleBoard());
        teamScheduleDto.setScheduleStart(teamScheduleEntity.getScheduleStart());
        teamScheduleDto.setScheduleEnd(teamScheduleEntity.getScheduleEnd());
        teamScheduleDto.setScheduleDone(teamScheduleEntity.getScheduleDone());
        teamScheduleDto.setMId(teamScheduleEntity.getMemberEntity().getMId());

        return teamScheduleDto;
    }

}
