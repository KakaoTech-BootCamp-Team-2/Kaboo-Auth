package kaboo.kaboo_auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kaboo.kaboo_auth.domain.dto.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class MainControllerAdvice {

	@ExceptionHandler({IllegalStateException.class, UsernameNotFoundException.class})
	public ResponseEntity<ResponseDTO<?>> exceptionHandler(Exception e) {
		log.error("[Kaboo-Auth]: 예외가 발생하였습니다. {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDTO<>(
						true,
						e.getMessage(),
						null
				));
	}
}
