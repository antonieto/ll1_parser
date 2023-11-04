import java.util.ArrayList;

/**
 * A class that represents a CFG production rule
 */
public class Rule {
    private NonTerminalSymbol ant;
    private String production;

    Rule(String ant, String production) {
        this.ant = new NonTerminalSymbol(ant);
        this.production = production;
    }

    public String toString() {
        StringBuffer str = new StringBuffer(this.ant.getSymbol());
        str.append(" -> ");
        str.append(this.production);
        return str.toString();
    }

    public String getProduction() {
        return this.production;
    }
}