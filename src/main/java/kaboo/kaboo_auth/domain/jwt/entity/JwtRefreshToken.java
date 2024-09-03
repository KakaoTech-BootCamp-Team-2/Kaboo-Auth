package kaboo.kaboo_auth.domain.jwt.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RedisHash(value = "JwtRefreshToken", timeToLive = 60 * 60 * 24 * 10) // 유효기간 : 10일
@RequiredArgsConstructor
public class JwtRefreshToken {
	@Id
	private final String username;
	private final String refreshToken;
}
