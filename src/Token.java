import java.util.Objects;

/**
 * Implementation for YYToken class
 */
public class Token {
    private final TokenType type;
    static Token EPSILON = new Token("EPSILON", TokenType.TERMINAL);
    static Token DOLLAR = new Token("$", TokenType.TERMINAL);
    static Token START = new Token("S", TokenType.NON_TERMINAL);
    private final String symbol;
    Token(String symbol, TokenType type) {
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

    public static Token nonTerminal(String symbol) {
        return new Token(symbol, TokenType.NON_TERMINAL);
    }

    public static Token terminal(String symbol) {
        return new Token(symbol, TokenType.TERMINAL);
    }
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if (!(o instanceof Token toCompare)) {
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