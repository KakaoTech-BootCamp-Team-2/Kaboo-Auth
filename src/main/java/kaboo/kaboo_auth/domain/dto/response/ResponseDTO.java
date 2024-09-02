package kaboo.kaboo_auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {
	private boolean success;
	private String message;
	private T data;

	public ResponseDTO(T data) {
		success = true;
		message = "요청이 성공적으로 처리되었습니다.";
		this.data = data;
	}
}
