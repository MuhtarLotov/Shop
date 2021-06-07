package team.alabs.shop.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String pattern, Object... params) {
        super(String.format(pattern, params));
    }
}
