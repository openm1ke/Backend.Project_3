package backend.shopapi.exceptions;

public class ImageIsEmptyException extends RuntimeException {

    public ImageIsEmptyException(String message) {
        super(message);
    }
}
