package kaboo.kaboo_auth.domain.jwt.repository;

import org.springframework.data.repository.CrudRepository;

import kaboo.kaboo_auth.domain.jwt.entity.JwtAccessToken;

public interface JwtAccessTokenRepository extends CrudRepository<JwtAccessToken, String> {
}
