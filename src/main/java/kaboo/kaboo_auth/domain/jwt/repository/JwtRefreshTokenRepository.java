package kaboo.kaboo_auth.domain.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import kaboo.kaboo_auth.domain.jwt.entity.JwtRefreshToken;

public interface JwtRefreshTokenRepository extends CrudRepository<JwtRefreshToken, String> {
}
