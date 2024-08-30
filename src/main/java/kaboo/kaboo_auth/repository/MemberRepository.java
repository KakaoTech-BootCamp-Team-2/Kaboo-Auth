package kaboo.kaboo_auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kaboo.kaboo_auth.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
}
