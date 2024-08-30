package kaboo.kaboo_auth.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String nickname;
	private String password;
	private String info;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Builder
	public Member(String username, String email, String nickname, String password, UserRole role) {
		this.username = username;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}
}
