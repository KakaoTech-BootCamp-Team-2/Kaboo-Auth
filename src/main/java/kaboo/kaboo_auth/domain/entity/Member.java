package kaboo.kaboo_auth.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@Setter
	private String username;

	@Setter
	private String nickname;

	@Setter
	private String password;

	@Setter
	private String info;

	@Builder
	public Member(String username, String nickname, String password) {
		this.username = username;
		this.nickname = nickname;
		this.password = password;
	}
}
