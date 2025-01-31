package cat.itacademy.s05.t01.n01.s05t01n01.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
