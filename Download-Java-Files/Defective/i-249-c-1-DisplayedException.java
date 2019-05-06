package org.kaccag.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DisplayedException {
    private final LocalDateTime timestamp;

    private final HttpStatus status;

    private final String error;

    private final String message;

    private final Throwable exception;

    /**
     * Should only be used to display errors to end users.
     * Only want classes within the package able to instantiate this exception.
     *
     * @param status
     * @param message
     */
    DisplayedException(final HttpStatus status, final String message) {
        this(status, message, null);
    }

    /**
     * Should only be used to display errors to end users.
     * Only want classes within the package able to instantiate this exception.
     *
     * @param status
     * @param message
     * @param exception
     */
    DisplayedException(final HttpStatus status, final String message, final Throwable exception) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = status.toString();
        this.message = message;
        this.exception = exception;
    }

    public String getTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
