package kaboo.kaboo_auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kaboo.kaboo_auth.domain.dto.request.MemberInfoUpdateRequest;
import kaboo.kaboo_auth.domain.dto.request.MemberIntroduceUpdateRequest;
import kaboo.kaboo_auth.domain.dto.response.MemberInfoResponse;
import kaboo.kaboo_auth.domain.dto.response.MemberIntroduceResponse;
import kaboo.kaboo_auth.domain.dto.response.MemberListResponse;
import kaboo.kaboo_auth.domain.dto.response.ResponseDTO;
import kaboo.kaboo_auth.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/all")
	public ResponseEntity<ResponseDTO<MemberListResponse>> getAllMembers() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						memberService.getAllMembers()
				));
	}

	@GetMapping("/class/{class}")
	public ResponseEntity<ResponseDTO<MemberListResponse>> getClassMembers(
			@PathVariable(name = "class") int classNum) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						memberService.getMembersByClassNum(classNum)
				));
	}

	@GetMapping("")
	public ResponseEntity<ResponseDTO<MemberInfoResponse>> getMemberInfo(
			@RequestParam(name = "name", defaultValue = "") String koreaName) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						memberService.getMemberInfoByKoreaName(koreaName)
				));
	}

	@PostMapping("")
	public ResponseEntity<ResponseDTO<MemberInfoResponse>> updateMemberInfo(
			@RequestParam(name = "name", defaultValue = "") String koreaName,
			@RequestBody MemberInfoUpdateRequest request) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						memberService.updateMemberInfoByKoreaName(koreaName, request)
				));
	}

	@GetMapping("/introduce")
	public ResponseEntity<ResponseDTO<MemberIntroduceResponse>> getMemberIntroduce(
			@RequestParam(name = "name", defaultValue = "") String koreaName) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						memberService.getMemberIntroduceByKoreaName(koreaName)
				));
	}

	@PostMapping("/introduce")
	public ResponseEntity<ResponseDTO<MemberIntroduceResponse>> updateMemberIntrouce(
			@RequestParam(name = "name", defaultValue = "") String koreaName,
			@RequestBody MemberIntroduceUpdateRequest request) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						memberService.updateMemberIntroduceByKoreaName(koreaName, request)
				));
	}
}
