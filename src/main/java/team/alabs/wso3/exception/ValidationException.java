package team.alabs.wso3.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String pattern, Object... params) {
        super(String.format(pattern, params));
    }
}
