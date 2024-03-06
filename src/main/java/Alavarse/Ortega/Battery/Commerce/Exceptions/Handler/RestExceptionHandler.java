package Alavarse.Ortega.Battery.Commerce.Exceptions.Handler;

import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.AddressNotFoundException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.ErrorWhileSavingAddressException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.BatteryNotFoundException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.ErrorWhileGettingBatteryException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.ErrorWhileSavingBatteryException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.*;
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
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(InvalidDocumentException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidDocument(InvalidDocumentException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InvalidDocumentSizeException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidDocumentSize(InvalidDocumentSizeException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(EmailAlredyExistsException.class)
    private ResponseEntity<ExceptionHandlerMessage> emailAlredyExists(EmailAlredyExistsException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(DocumentAlredyExistsException.class)
    private ResponseEntity<ExceptionHandlerMessage> documentAlredyExists(DocumentAlredyExistsException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileSavingUserException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorSavingUser(ErrorWhileSavingUserException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ExceptionHandlerMessage> userNotFound(UserNotFoundException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerMessage);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    private ResponseEntity<ExceptionHandlerMessage> addressNotFound(AddressNotFoundException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileSavingAddressException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorSavingAddress(ErrorWhileSavingAddressException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(BatteryNotFoundException.class)
    private ResponseEntity<ExceptionHandlerMessage> batteryNotFound(BatteryNotFoundException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileGettingBatteryException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingBattery(ErrorWhileGettingBatteryException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileSavingUserException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileSavingBattery(ErrorWhileSavingBatteryException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

}
