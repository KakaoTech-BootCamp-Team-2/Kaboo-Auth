package kaboo.kaboo_auth.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {
	private boolean success;
	private String message;
	private T data;
}
