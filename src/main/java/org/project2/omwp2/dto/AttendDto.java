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
public class AttendDto {

    private Long attendId;
    private LocalDateTime attendStart;
    private LocalDateTime attendEnd;

}
