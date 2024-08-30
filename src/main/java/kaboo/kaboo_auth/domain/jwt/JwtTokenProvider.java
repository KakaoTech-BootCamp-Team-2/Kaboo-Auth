package kaboo.kaboo_auth.domain.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final String accessSecretKey;
	private final String refreshSecretKey;
	private final String issuer;

	private final long accessTokenValidTime = 10 * 60 * 1_000L; // 유효기간: 10분
	private final long refreshTokenValidTime = 10 * 24 * 60 * 60 * 1_000L; // 유효기간: 10일

	public JwtTokenProvider(
			@Value("${JWT.ACCESS_SECRET_KEY}") String accessSecretKey,
			@Value("${JWT.REFRESH_SECRET_KEY}") String refreshSecretKey,
			@Value("${JWT.ISSUER}") String issuer) {

		this.accessSecretKey = accessSecretKey;
		this.refreshSecretKey = refreshSecretKey;
		this.issuer = issuer;
	}

	private SecretKey getSignedKey(String key) {
		byte[] keyBytes = Decoders.BASE64.decode(key);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(String username) {
		Date now = new Date();

		return Jwts.builder()
				.subject(username)
				.issuer(issuer)
				.issuedAt(now)
				.expiration(new Date(now.getTime() + accessTokenValidTime))
				.signWith(getSignedKey(accessSecretKey))
				.compact();
	}

	public String createRefreshToken(String username) {
		Date now = new Date();

		return Jwts.builder()
				.subject(username)
				.issuer(issuer)
				.issuedAt(now)
				.expiration(new Date(now.getTime() + refreshTokenValidTime))
				.signWith(getSignedKey(refreshSecretKey))
				.compact();
	}
}
