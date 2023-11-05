import java.util.List;
import java.util.Optional;

/**
 * A class that represents a CFG production rule
 */
public class Rule {
    private NonTerminalSymbol ant;
    private List<YYToken> production;

    Rule(String ant, List<YYToken> production) {
        this.ant = new NonTerminalSymbol(ant);
        this.production = production;
    }

    public String toString() {
        StringBuffer str = new StringBuffer(this.ant.getSymbol());
        str.append(" -> ");
        str.append(this.production);
        return str.toString();
    }

    public Optional<String> getProduction() {
        return this.production.stream().map(YYToken::toString).reduce((el, acc) ->  acc + el);
    }
}
