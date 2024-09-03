package kaboo.kaboo_auth.domain.dto.request;

import kaboo.kaboo_auth.domain.Course;
import lombok.Getter;

@Getter
public class MemberInfoUpdateRequest {
	private String koreaName;
	private String englishName;
	private int classNum;
	private Course course;

}
