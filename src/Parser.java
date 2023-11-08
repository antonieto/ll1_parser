import java.text.ParseException;
import java.util.Stack;

/**
 * Implementation of a parser that takes in a stream of tokens.
 */
public class Parser {
    private final ParsingTable table;
    Parser(ParsingTable table) {
        this.table = table;
    }
    public void parse(Token[] tokens) throws ParseException {
        Stack<Token> stack = new Stack<>();
        stack.push(Token.START);
        int current = 0;
        while (!stack.isEmpty() && current < tokens.length) {
            Token top = stack.peek();
            Token currentToken = tokens[current];
            if (top.isTerminal() || top.equals(Token.DOLLAR)) {
                if (top.equals(currentToken)) {
                    stack.pop();
                    current++;
                } else {
                    throw new ParseException("Failed to parse here", current);
                }
            } else if(top.isNonTerminal()){
                if (table.at(top, currentToken).isPresent()) {
                    Rule rule = table.at(top, currentToken).get();
                    stack.pop();
                    System.out.println("Added rule: " + rule);
                    for (Token token: rule.production().reversed()) {
                        if(!token.equals(Token.EPSILON)) stack.push(token);
                    }
                } else {

                    throw new ParseException("Failed to parse", current);
                }
            }
        }
    }
}
