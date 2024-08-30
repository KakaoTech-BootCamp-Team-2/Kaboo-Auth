package kaboo.kaboo_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kaboo.kaboo_auth.domain.entity.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
