package wolox.training.exceptions.handler;

public enum ErrorsEnum {

    JSON_BOOK_NOT_FOUND_EXCEPTION("404", "Book Not Found"),
    JSON_BAD_REQUEST_EXCEPTION("400", "Bad Request"),
    JSON_USER_NOT_FOUND_EXCEPTION("404", "User Not Found");

    private final String code;
    private final String message;

    ErrorsEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
