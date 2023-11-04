import java.util.Stack;

/**
 * Implementation of a parser that takes in a stream of tokens.
 */
public class Parser {

    private Stack<YYToken> stack;
    private ParsingTable table;

    Parser(ParsingTable table) {
        this.table = table;
        this.stack = new Stack<YYToken>();
        this.stack.push(YYToken.DOLLAR);
        this.stack.push(new YYToken("S", TokenType.NON_TERMINAL));
    }
    // Takes next symbol in stream, returns true if it finished processing
    public boolean nextSymbol(YYToken token) {
        return true;
    }
}
