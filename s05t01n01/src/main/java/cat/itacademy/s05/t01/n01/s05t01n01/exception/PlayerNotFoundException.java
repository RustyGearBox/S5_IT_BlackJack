package cat.itacademy.s05.t01.n01.s05t01n01.exception;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
