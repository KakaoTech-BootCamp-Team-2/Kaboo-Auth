package kaboo.kaboo_auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/")
	public String mainAPI() {
		return "누구나 접근가능한 API입니다.";
	}

	@GetMapping("/test")
	public String authAPI() {
		return "권한 Test API 입니다.";
	}

	@GetMapping("/auth/hello")
	public String helloAuth(Authentication authentication) {
		return "인가 받은 사용자 " + authentication.getName() + " 님 환영합니다.";
	}
}
