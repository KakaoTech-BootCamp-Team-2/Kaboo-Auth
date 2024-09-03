package kaboo.kaboo_auth.domain.jwt.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RedisHash(value = "JwtAccessToken", timeToLive = 60 * 10) // 유효기간 : 10분
@RequiredArgsConstructor
public class JwtAccessToken {
	@Id
	private final String username;
	private final String accessToken;
}
