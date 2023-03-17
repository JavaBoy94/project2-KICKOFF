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
public class NoticeDto {

    private Long noticeId;
    private LocalDateTime noticeCreate;
    private LocalDateTime noticeUpdate;
    private String noticeTitle;
    private String noticeContent;
    private int noticeHit;

}
