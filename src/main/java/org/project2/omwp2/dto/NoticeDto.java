package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.NoticeEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "제목을 입력해주세요")
    @Size(min=3, message = "최소 3자이상 입력해주세요")
    private String noticeTitle;

    @NotBlank(message = "내용을 입력해주세요")
    private String noticeContent;

    private int noticeHit;

    private String mEmail;

    private String mName;

    public static NoticeDto toNoticeDto(NoticeEntity noticeEntity) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoticeId(noticeEntity.getNoticeId());
        noticeDto.setNoticeTitle(noticeEntity.getNoticeTitle());
        noticeDto.setNoticeContent(noticeEntity.getNoticeContent());
        noticeDto.setNoticeCreate(noticeEntity.getNoticeCreate());
        noticeDto.setNoticeUpdate(noticeEntity.getNoticeUpdate());
        noticeDto.setNoticeHit(noticeEntity.getNoticeHit());
        noticeDto.setMEmail(noticeEntity.getMemberEntity().getMEmail());
        noticeDto.setMName(noticeEntity.getMemberEntity().getMName());

        return noticeDto;
    }
}
