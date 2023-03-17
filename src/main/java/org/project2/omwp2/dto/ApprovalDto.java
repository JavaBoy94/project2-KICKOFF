package org.project2.omwp2.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApprovalDto {

    private Long appId;
    private String appDivision;
    private String appTitle;
    private String appContent;
    private LocalDateTime appCreate;
    private LocalDateTime appUpdate;
    private int appStatus;
    private String appReason;
    private int appAttach;

}
