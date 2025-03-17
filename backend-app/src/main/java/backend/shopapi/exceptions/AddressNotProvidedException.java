package backend.shopapi.exceptions;

public class AddressNotProvidedException extends RuntimeException {
    public AddressNotProvidedException(String message) {
        super(message);
    }
}
