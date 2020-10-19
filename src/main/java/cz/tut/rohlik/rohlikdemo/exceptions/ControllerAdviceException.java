package cz.tut.rohlik.rohlikdemo.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.AccessControlException;

@ControllerAdvice
@Log4j2
public class ControllerAdviceException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomError> notFoundException(AccessControlException ex,
                                                         HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new CustomError(
                                     HttpStatus.NOT_FOUND.toString(),
                                     ex.getMessage()
                             ));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomError> badRequestException(BadRequestException ex,
                                                           HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new CustomError(
                                     HttpStatus.BAD_REQUEST.toString(),
                                     ex.getMessage()
                             ));
    }

    @ExceptionHandler(CancelOrderException.class)
    public ResponseEntity<CustomError> cancelOrderException(CancelOrderException ex,
                                                            HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new CustomError(
                                     HttpStatus.BAD_REQUEST.toString(),
                                     ex.getMessage()
                             ));
    }

    @ExceptionHandler(ItemCountException.class)
    public ResponseEntity<CustomError> itemCountException(ItemCountException ex,
                                                          HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new CustomError(
                                     HttpStatus.BAD_REQUEST.toString(),
                                     ex.getMessage()
                             ));
    }
}
