package kaboo.kaboo_auth.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kaboo.kaboo_auth.domain.Course;
import kaboo.kaboo_auth.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String username;
	private String email;
	private String koreaName;
	private String englishName;
	private String password;
	private String info;

	private int classNum;

	@Enumerated(EnumType.STRING)
	private Course course;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Builder
	public Member(String username, String email, String koreaName, String englishName, String password, String info,
			int classNum, Course course, UserRole role) {
		this.username = username;
		this.email = email;
		this.koreaName = koreaName;
		this.englishName = englishName;
		this.password = password;
		this.info = info;
		this.classNum = classNum;
		this.course = course;
		this.role = role;
	}
}
