package kaboo.kaboo_auth.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import kaboo.kaboo_auth.domain.Course;
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
		Member member = Member.builder().username("Alice").englishName("Alice").password("1234").build();
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
		Member member1 = Member.builder().username("Alice").englishName("Alice").password("1234").build();
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

	@Test
	@DisplayName("DB에 존재하는 classNum 찾을 때 findByClassNum method 테스트")
	void findByClassNum_Success() {
		// Given
		Member member1 = Member.builder().englishName("Alice").classNum(1).build();
		Member member2 = Member.builder().englishName("Bob").classNum(1).build();

		memberRepository.save(member1);
		memberRepository.save(member2);

		List<Member> expectedMembers = Arrays.asList(member1, member2);

		// When
		List<Member> byClassNum = memberRepository.findByClassNum(1);

		// Then
		assertEquals(byClassNum.size(), 2, "리스트의 크기가 다릅니다.");
		assertTrue(byClassNum.containsAll(expectedMembers), "리스트에 모든 멤버가 포함되어 있지 않습니다.");
	}

	@Test
	@DisplayName("DB에 존재하지 않는 classNum 찾을 때 findByClassNum method 테스트")
	void findByClassNum_Failure() {
		// Given

		// When
		List<Member> byClassNum = memberRepository.findByClassNum(1);

		// Then
		assertEquals(byClassNum.size(), 0);
	}

	@Test
	@DisplayName("findByKoreaName 성공 테스트")
	void findByKoreaName_Success() {
		// Given
		Member member = Member.builder()
				.koreaName("홍길동")
				.classNum(1)
				.course(Course.AI)
				.build();
		memberRepository.save(member);

		// When
		Optional<Member> byKoreaName = memberRepository.findByKoreaName("홍길동");

		// Then
		assertEquals(byKoreaName.get(), member);
	}

	@Test
	@DisplayName("findByKoreaName 실패 테스트")
	void findByKoreaName_Failure() {
		// Given

		// When
		Optional<Member> byKoreaName = memberRepository.findByKoreaName("홍길동");

		// Then
		assertTrue(byKoreaName.isEmpty());
	}
}
