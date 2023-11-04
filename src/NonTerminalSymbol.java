/**
 * Class that inherits from Token. Used only for checking at Parsing Table.
 */
public class NonTerminalSymbol extends Token {
    NonTerminalSymbol(String symbol) {
        super(symbol);
    }
}
