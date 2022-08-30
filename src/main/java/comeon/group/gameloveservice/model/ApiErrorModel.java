package comeon.group.gameloveservice.model;

import org.springframework.http.HttpStatus;

import java.util.Date;


public class ApiErrorModel {
    private Date timestamp;
    private String path;
    private HttpStatus status;
    private String error;
    private String message;
    private String details;

    public ApiErrorModel(String path, HttpStatus status, String message) {
        this.timestamp = new Date();
        this.path = path;
        this.status = status;
        this.error = status.getReasonPhrase();
        this.message = message;
    }

    public ApiErrorModel(String path, HttpStatus status, String message, String details) {
        this(path, status, message);
        this.details = details;
    }
}
