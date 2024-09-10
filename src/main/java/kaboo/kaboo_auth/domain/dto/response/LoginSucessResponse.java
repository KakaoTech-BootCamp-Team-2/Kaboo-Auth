package kaboo.kaboo_auth.domain.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginSucessResponse implements Serializable {
	private String username;
	private String accessToken;
	private String refreshToken;
}
