package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.TeamScheduleEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TeamScheduleDto {

    private Long ScheduleId;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date start;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date end;

    private String scheduleDone;

    private Long mId;


    public static TeamScheduleDto toTeamScheduleDto(TeamScheduleEntity teamScheduleEntity){

        TeamScheduleDto teamScheduleDto = new TeamScheduleDto();

        teamScheduleDto.setScheduleId(teamScheduleEntity.getScheduleId());
        teamScheduleDto.setContent(teamScheduleEntity.getContent());
        teamScheduleDto.setStart(teamScheduleEntity.getStart());
        teamScheduleDto.setEnd(teamScheduleEntity.getEnd());
        teamScheduleDto.setScheduleDone(teamScheduleEntity.getScheduleDone());
        teamScheduleDto.setMId(teamScheduleEntity.getMemberEntity().getMId());

        return teamScheduleDto;
    }

}
