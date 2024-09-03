package kaboo.kaboo_auth.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class ProfileImage {
	@Id
	@Column(name = "profile_image_name")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Setter
	@Column(name = "image_name")
	private String imageName;

	public ProfileImage(Member member, String imageName) {
		this.member = member;
		this.imageName = imageName;
	}
}
