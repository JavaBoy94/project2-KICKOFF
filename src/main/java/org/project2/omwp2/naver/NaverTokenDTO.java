package org.project2.omwp2.naver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverTokenDTO {
	
	private String access_token;
	private String refresh_token;
	private String scope;
	private String token_type;
	private String expires_in;

}
