package wolox.training.exceptions.handler;

public enum ErrorsEnum {

    JSON_BOOK_NOT_FOUND_EXCEPTION("404", "Book Not Found"),
<<<<<<< HEAD
    JSON_BOOK_ID_MISMATCH_EXCEPTION("400", "Bad Request");
=======
    JSON_BOOK_ID_MISMATCH_EXCEPTION("400", "Book Id Not Found"),
    JSON_BOOK_ALREADY_OWNED_EXCEPTION("404", "Book Already Owned");
>>>>>>> create-user-model

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
