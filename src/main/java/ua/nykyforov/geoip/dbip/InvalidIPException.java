package ua.nykyforov.geoip.dbip;

public class InvalidIPException extends RuntimeException {
    private String message;

    public InvalidIPException(final String message) {
        super(message);
    }
}
