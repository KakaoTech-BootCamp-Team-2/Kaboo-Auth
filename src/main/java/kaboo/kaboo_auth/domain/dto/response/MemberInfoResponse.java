package kaboo.kaboo_auth.domain.dto.response;

import kaboo.kaboo_auth.domain.Course;
import kaboo.kaboo_auth.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
	private String koreaName;
	private String englishName;
	private int classNum;
	private Course course;

	public MemberInfoResponse(Member member) {
		this.koreaName = member.getKoreaName();
		this.englishName = member.getEnglishName();
		this.classNum = member.getClassNum();
		this.course = member.getCourse();
	}
}
