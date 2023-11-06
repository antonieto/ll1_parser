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
    public void parse(YYToken[] tokens) throws ParseException {
        Stack<YYToken> stack = new Stack<>();
        stack.push(YYToken.START);
        int current = 0;
        while (!stack.isEmpty() && current < tokens.length) {
            YYToken top = stack.peek();
            YYToken currentToken = tokens[current];
            if (top.isTerminal() || top.equals(YYToken.DOLLAR)) {
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
                    for (YYToken token: rule.production().reversed()) {
                        if(!token.equals(YYToken.EPSILON)) stack.push(token);
                    }
                } else {

                    throw new ParseException("Failed to parse", current);
                }
            }
        }
    }
}
