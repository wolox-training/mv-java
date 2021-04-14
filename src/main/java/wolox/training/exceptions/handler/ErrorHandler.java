package wolox.training.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;

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

        return new ErrorResponse(ErrorsEnum.JSON_BAD_REQUEST_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BAD_REQUEST_EXCEPTION.getMessage());
    }

    /**
     *
     * @param ex: Exception BookAlreadyOwnedException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(BookAlreadyOwnedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse bookAlreadyOwnedException(BookAlreadyOwnedException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BAD_REQUEST_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BAD_REQUEST_EXCEPTION.getMessage());
    }

    /**
     *
     * @param ex: Exception UserNotFoundException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ErrorResponse userNotFoundException(UserNotFoundException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_USER_NOT_FOUND_EXCEPTION.getCode(),
                ErrorsEnum.JSON_USER_NOT_FOUND_EXCEPTION.getMessage());
    }

    /**
     *
     * @param ex: Exception UserIdMismatchException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(UserIdMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse userIdMismatchException(UserIdMismatchException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BAD_REQUEST_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BAD_REQUEST_EXCEPTION.getMessage());
    }

}
