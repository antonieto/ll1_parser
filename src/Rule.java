import java.util.List;

/**
 * A class that represents a CFG production rule
 */
public class Rule {
    final private Token ant;
    final private List<Token> production;

    Rule(String ant, List<Token> production) {
        this.ant = Token.nonTerminal(ant);
        this.production = production;
    }

    public String toString() {
        return this.ant.toString() + " -> " +
                this.production.stream().map(Token::toString).reduce((acc, el) -> acc + el).get();
    }

    public List<Token> production() {
        return this.production;
    }
}
