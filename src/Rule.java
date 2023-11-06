import java.util.List;

/**
 * A class that represents a CFG production rule
 */
public class Rule {
    final private NonTerminalSymbol ant;
    final private List<YYToken> production;

    Rule(String ant, List<YYToken> production) {
        this.ant = new NonTerminalSymbol(ant);
        this.production = production;
    }

    public String toString() {
        return this.ant.getSymbol() + " -> " +
                this.production.stream().map(YYToken::toString).reduce((acc, el) -> acc + el).get();
    }

    public List<YYToken> production() {
        return this.production;
    }
}
