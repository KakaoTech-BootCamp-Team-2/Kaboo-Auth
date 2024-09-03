package kaboo.kaboo_auth.domain.dto.response;

public interface OAuth2Response {
	String getProvider();

	String getProviderId();

	String getEmail();

	String getNickname();
}
