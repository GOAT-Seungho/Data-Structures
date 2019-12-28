public class NoSuchTermExistsException extends RuntimeException {
    public NoSuchTermExistsException() {
    }

    public NoSuchTermExistsException(final String message) {
        super(message);
    }
}
