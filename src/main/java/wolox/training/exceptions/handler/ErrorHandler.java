package wolox.training.exceptions.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    protected ErrorResponse BookNotFoundException(BookNotFoundException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BOOK_NOT_FOUND_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BOOK_NOT_FOUND_EXCEPTION.getMessage());
    }

    /**
     *
     * @param ex: Exception BookIdMismathException
     * @return ErrorResponse with the exception code and the message
     */
    @ExceptionHandler(BookIdMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse BookIdMismatchException(BookIdMismatchException ex) {

        return new ErrorResponse(ErrorsEnum.JSON_BOOK_ID_MISMATCH_EXCEPTION.getCode(),
                ErrorsEnum.JSON_BOOK_ID_MISMATCH_EXCEPTION.getMessage());
    }

}
