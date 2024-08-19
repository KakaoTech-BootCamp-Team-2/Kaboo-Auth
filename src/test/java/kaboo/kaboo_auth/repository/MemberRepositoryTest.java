package kaboo.kaboo_auth.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import kaboo.kaboo_auth.domain.entity.Member;

@DataJpaTest
@Transactional
@DisplayName("Member Repository Test")
class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("DB 저장 Test")
	void saveMemberTest() {
		// Given
		Member member = Member.builder().username("Alice").nickname("Alice").password("1234").build();
		memberRepository.save(member);

		// When
		Member result = memberRepository.findById(member.getId()).get();

		// Then
		assertEquals(result, member);
	}

	@Test
	@DisplayName("Username으로 찾기 성공 Test")
	void findByUsername_Success() {
		// Given
		Member member1 = Member.builder().username("Alice").nickname("Alice").password("1234").build();
		memberRepository.save(member1);

		// When
		Member result = memberRepository.findByUsername("Alice").get();

		// Then
		assertEquals(result, member1);
	}

	@Test
	@DisplayName("Username 존재하지 않을 때 Test")
	void findByUsername_Failure() {
		// Given

		// When
		Optional<Member> result = memberRepository.findByUsername("Alice");

		// Then
		assertEquals(result, Optional.empty());
	}
}