package org.project2.omwp2.naver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class OrgResponse {

	private List<OrgUnit> orgUnits;
	private ResponseMetaData responseMetaData;

}
