package kaboo.kaboo_auth.domain.dto.response;

import kaboo.kaboo_auth.domain.Course;
import kaboo.kaboo_auth.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponse {
	private final String koreaName;
	private final String englishName;
	private final int classNum;
	private final Course course;

	public MemberInfoResponse(Member member) {
		this.koreaName = member.getKoreaName();
		this.englishName = member.getEnglishName();
		this.classNum = member.getClassNum();
		this.course = member.getCourse();
	}
}
