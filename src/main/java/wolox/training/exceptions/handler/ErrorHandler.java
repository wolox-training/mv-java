package wolox.training.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;

@ControllerAdvice
public class ErrorHandler {

    /**
     *
     * @param ex: Exception BookNotFoundException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ErrorResponse bookNotFoundException(BookNotFoundException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BOOK_NOT_FOUND_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BOOK_NOT_FOUND_EXCEPTION.getMessage());
    }

    /**
     *
     * @param ex: Exception BookIdMismatchException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(BookIdMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse bookIdMismatchException(BookIdMismatchException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BOOK_ID_MISMATCH_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BOOK_ID_MISMATCH_EXCEPTION.getMessage());
    }

    /**
     *
     * @param ex: Exception BookAlreadyOwnedException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(BookAlreadyOwnedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse BookAlreadyOwnedException(BookAlreadyOwnedException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BOOK_ALREADY_OWNED_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BOOK_ALREADY_OWNED_EXCEPTION.getMessage());
    }

}
