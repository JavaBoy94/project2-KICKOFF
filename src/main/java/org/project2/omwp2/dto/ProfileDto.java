package org.project2.omwp2.dto;

import lombok.*;
import org.project2.omwp2.entity.ProfileEntity;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProfileDto {

    private Long profileId;
    private String profileOrigin;
    private String profileSave;

    public static ProfileDto toProfileDto(ProfileEntity profileEntity){
        ProfileDto profileDto = new ProfileDto();

        profileDto.setProfileOrigin(profileEntity.profileOrigin);
        profileDto.setProfileSave(profileEntity.profileSave);

        return profileDto;
    }
}
