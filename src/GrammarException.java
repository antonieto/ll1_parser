public class GrammarException extends Exception {
    private final String message;

    GrammarException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
