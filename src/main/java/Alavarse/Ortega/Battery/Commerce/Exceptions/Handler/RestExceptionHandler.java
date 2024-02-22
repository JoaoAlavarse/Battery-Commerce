package Alavarse.Ortega.Battery.Commerce.Exceptions.Handler;

import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.DoesntContainNumbersException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.DoesntContainSpecialCharacterException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.InvalidEmailException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.InvalidPasswordSizeException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.ErrorWhileGettingUsersException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.InvalidDocumentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.InvalidDocumentSizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidEmail(InvalidEmailException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(DoesntContainSpecialCharacterException.class)
    private ResponseEntity<ExceptionHandlerMessage> doesntContainSpecialCharacter(DoesntContainSpecialCharacterException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(DoesntContainNumbersException.class)
    private ResponseEntity<ExceptionHandlerMessage> doesntContainNumbers(DoesntContainNumbersException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InvalidPasswordSizeException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidPasswordSize(InvalidPasswordSizeException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileGettingUsersException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingUsers(ErrorWhileGettingUsersException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InvalidDocumentException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingUsers(InvalidDocumentException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InvalidDocumentSizeException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingUsers(InvalidDocumentSizeException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }
}
