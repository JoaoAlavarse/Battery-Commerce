package Alavarse.Ortega.Battery.Commerce.Exceptions.Handler;

import Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions.*;
import Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;


@ControllerAdvice
public class RestExceptionHandler  {

    private String extractFieldName(Exception exception) {

        Throwable rootCause = exception.getCause();
        if (rootCause instanceof Exception) {
            MismatchedInputException mismatchedInputException = (MismatchedInputException) rootCause;
            List<JsonMappingException.Reference> path = mismatchedInputException.getPath();
            if (!path.isEmpty()) {
                String fieldName = path.get(path.size() - 1).getFieldName();
                return fieldName;
            }
        }
        return "abc";
    }

    @ExceptionHandler(InvalidEmailException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidEmail(InvalidEmailException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "email");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(DoesntContainSpecialCharacterException.class)
    private ResponseEntity<ExceptionHandlerMessage> doesntContainSpecialCharacter(DoesntContainSpecialCharacterException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(DoesntContainNumbersException.class)
    private ResponseEntity<ExceptionHandlerMessage> doesntContainNumbers(DoesntContainNumbersException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InvalidPasswordSizeException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidPasswordSize(InvalidPasswordSizeException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileGettingUsersException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingUsers(ErrorWhileGettingUsersException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(InvalidDocumentException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidDocument(InvalidDocumentException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "document");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InvalidDocumentSizeException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidDocumentSize(InvalidDocumentSizeException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "document");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    private ResponseEntity<ExceptionHandlerMessage> emailAlreadyExists(EmailAlreadyExistsException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), "email");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(DocumentAlreadyExistsException.class)
    private ResponseEntity<ExceptionHandlerMessage> documentAlreadyExists(DocumentAlreadyExistsException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), "document");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ExceptionHandlerMessage> userNotFound(UserNotFoundException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileSavingUserException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorSavingUser(ErrorWhileSavingUserException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
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

    @ExceptionHandler(ErrorWhileSavingBatteryException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileSavingBattery(ErrorWhileSavingBatteryException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileGettingPromotionException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingPromotion(ErrorWhileGettingPromotionException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileSavingPromotionException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileSavingPromotion(ErrorWhileSavingPromotionException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(InvalidPromotionException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidPromotion(InvalidPromotionException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(PromotionAlreadyBeenUsedException.class)
    private ResponseEntity<ExceptionHandlerMessage> promotionAlreadyBeenUsed(PromotionAlreadyBeenUsedException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(PromotionNotFoundException.class)
    private ResponseEntity<ExceptionHandlerMessage> promotionAlreadyBeenUsed(PromotionNotFoundException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerMessage);
    }

    @ExceptionHandler(PromotionAlreadyExists.class)
    private ResponseEntity<ExceptionHandlerMessage> promotionAlreadyExists(PromotionAlreadyExists exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(InvalidExpirationDateException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidDate(InvalidExpirationDateException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), "expirationDate");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(LoginFailedException.class)
    private ResponseEntity<ExceptionHandlerMessage> failedLogin(LoginFailedException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.FORBIDDEN, exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(handlerMessage);
    }

    @ExceptionHandler(CartNotFoundException.class)
    private ResponseEntity<ExceptionHandlerMessage> cartNotFound(CartNotFoundException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(handlerMessage);
    }

    @ExceptionHandler(BatteryAlreadyExistsInException.class)
    private ResponseEntity<ExceptionHandlerMessage> batteryAlreadyExistsIn(BatteryAlreadyExistsInException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(BatteryDoenstExistsInException.class)
    private ResponseEntity<ExceptionHandlerMessage> batteryDoesntExistsIn(BatteryDoenstExistsInException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileAddingPromotionException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileAddingPromotion(ErrorWhileAddingPromotionException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileRemovingPromotionException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileRemovingPromotion(ErrorWhileRemovingPromotionException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileCalculatingCartTotalValueException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileCalculatingCartTotalValue(ErrorWhileCalculatingCartTotalValueException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileGettingCartException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileGettingCart(ErrorWhileGettingCartException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileSavingCartException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorWhileSavingCart(ErrorWhileSavingCartException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(handlerMessage);
    }

    @ExceptionHandler(InsufficientBatteriesToAddException.class)
    private ResponseEntity<ExceptionHandlerMessage> insufficientBatteriesToAdd(InsufficientBatteriesToAddException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ExceptionHandlerMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, "Campo obrigatório", exception.getBindingResult().getFieldError().getField());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(InconsistentPasswordsException.class)
    private ResponseEntity<ExceptionHandlerMessage> inconsistentPasswords(InconsistentPasswordsException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, exception.getMessage(), "confirmPassword");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    private ResponseEntity<ExceptionHandlerMessage> unauthorizedUser(UnauthorizedUserException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.UNAUTHORIZED, exception.getMessage(), "confirmPassword");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(handlerMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ExceptionHandlerMessage> typeMismatch(HttpMessageNotReadableException exception){
        String fieldName = extractFieldName(exception);
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.BAD_REQUEST, "Tipo de dado inválido", fieldName);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handlerMessage);
    }

    @ExceptionHandler(ErrorWhileChangingRolesException.class)
    private ResponseEntity<ExceptionHandlerMessage> errorChangingRoles(ErrorWhileChangingRolesException exception){
        String fieldName = extractFieldName(exception);
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), "role");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(SameUserRoleChangeException.class)
    private ResponseEntity<ExceptionHandlerMessage> sameUserRoleChange(SameUserRoleChangeException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), "role");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(BatteryAlreadyExistsException.class)
    private ResponseEntity<ExceptionHandlerMessage> batteryAlreadyExistsException(BatteryAlreadyExistsException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), "code");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    private ResponseEntity<ExceptionHandlerMessage> invalidPassword(InvalidPasswordException exception){
        ExceptionHandlerMessage handlerMessage = new ExceptionHandlerMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(handlerMessage);
    }


}
