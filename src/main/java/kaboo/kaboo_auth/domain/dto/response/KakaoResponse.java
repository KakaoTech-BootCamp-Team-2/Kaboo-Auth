package kaboo.kaboo_auth.domain.dto.response;

import java.util.Map;

/** Kakao Response 형태
 * {
 *     id: int,
 *     connected_at: Date
 *     properties: {
 *         nickname: String
 *         profile_image: URL
 *         thumbnail_image: URL
 *     },
 *     kakao_account: {
 *			profile_nickname_needs_agreement: boolean,
 *			profile_image_needs_agreement: boolean
 *			profile: {
 *				nickname: String
 *				thumbnail_image_url: URL
 *				profile_image_url: URL
 *				is_default_image: boolean
 *				is_default_nickname: boolean
 *          },
 *     		has_email=true,
 *     		email_needs_agreement=false,
 *     		is_email_valid=true,
 *     		is_email_verified=true,
 *     		email=taejin7824@kakao.com
 *     }
 *
 * }
 */

public class KakaoResponse implements OAuth2Response {
	private final Map<String, Object> attribute;

	public KakaoResponse(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getProviderId() {
		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {
		Map<String, Object> kakaoAccount = (Map<String, Object>)attribute.get("kakao_account");

		// "kakao_account" 맵에서 "email" 키의 값을 가져옴
		if (kakaoAccount != null && kakaoAccount.containsKey("email")) {
			return kakaoAccount.get("email").toString();
		}
		return null; // email이 없을 경우
	}

	@Override
	public String getNickname() {
		Map<String, Object> properties = (Map<String, Object>)attribute.get("properties");

		// "properties" 맵에서 "nickname" 키의 값을 가져옴
		if (properties != null && properties.containsKey("nickname")) {
			return properties.get("nickname").toString();
		}
		return null; // nickname이 없을 경우
	}
}
