package kaboo.kaboo_auth.domain.dto.response;

import kaboo.kaboo_auth.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
	private String koreaName;
	private String englishName;
	private int classNum;
	private Course course;
}
