public class Token {
    private final String symbol;
    Token(String symbol)  {
        this.symbol = symbol;
    }

    String getSymbol() {
        return String.valueOf(symbol);
    }
}
