package comeon.group.gameloveservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {
  private static final long serialVersionUID = 7246983447306271525L;

  public AlreadyExistsException(String message) {
    super(message);
  }
}
