import java.util.Objects;

/**
 * Implementation for YYToken class
 */
public class YYToken {
    public TokenType type;
    static YYToken EPSILON = new YYToken("", TokenType.NON_TERMINAL);
    static YYToken DOLLAR = new YYToken("$", TokenType.TERMINAL);
    private String symbol;
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

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if (!(o instanceof YYToken toCompare)) {
            return false;
        }

        // Typecast
        if (this.type != toCompare.type) {
            return false;
        }

        return this.symbol.equals(toCompare.symbol);
    }
}

enum TokenType {
    TERMINAL,
    NON_TERMINAL,
}