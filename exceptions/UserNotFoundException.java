package exceptions;
public class UserNotFoundException extends IllegalArgumentException{
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message){
        super(message);
    }
}
