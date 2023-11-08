import java.util.List;

/**
 * A class that represents a CFG production rule
 */
public class Rule {
    final private YYToken ant;
    final private List<YYToken> production;

    Rule(String ant, List<YYToken> production) {
        this.ant = YYToken.nonTerminal(ant);
        this.production = production;
    }

    public String toString() {
        return this.ant.toString() + " -> " +
                this.production.stream().map(YYToken::toString).reduce((acc, el) -> acc + el).get();
    }

    public List<YYToken> production() {
        return this.production;
    }
}
