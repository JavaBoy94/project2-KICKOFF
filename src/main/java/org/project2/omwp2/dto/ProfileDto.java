package org.project2.omwp2.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProfileDto {

    private Long profileId;
    public String docOrigin;
    public String docSave;

}
