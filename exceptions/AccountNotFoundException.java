package exceptions;
public class AccountNotFoundException extends IllegalArgumentException{

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message){
        super(message);
    }
    
}
