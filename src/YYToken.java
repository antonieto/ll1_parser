import java.util.Objects;

/**
 * Implementation for YYToken class
 */
public class YYToken {
    private final TokenType type;
    static YYToken EPSILON = new YYToken("EPSILON", TokenType.TERMINAL);
    static YYToken DOLLAR = new YYToken("$", TokenType.TERMINAL);
    static YYToken START = new YYToken("S", TokenType.NON_TERMINAL);
    private final String symbol;
    YYToken(String symbol, TokenType type) {
        this.symbol = symbol;
        this.type = type;
    }

    public boolean isTerminal() {
        return type == TokenType.TERMINAL;
    }

    public boolean isNonTerminal() {
        return type == TokenType.NON_TERMINAL;
    }

    public String toString() {
        return this.symbol;
    }

    static YYToken nonTerminal(String symbol) {
        return new YYToken(symbol, TokenType.NON_TERMINAL);
    }

    static YYToken terminal(String symbol) {
        return new YYToken(symbol, TokenType.TERMINAL);
    }
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if (!(o instanceof YYToken toCompare)) {
            return false;
        }

        // Typecast
        if (!this.type.equals(toCompare.type)) {
            return false;
        }

        return this.symbol.equals(toCompare.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, type);
    }
}

enum TokenType {
    TERMINAL,
    NON_TERMINAL,
}