import java.util.*;

/**
 * A parsing table that returns a Rule if given at the intersection of token i, j.
 */
public class ParsingTable {
    // Use a Map of Maps
    final private HashMap<String, HashMap<String, Rule>> table;
    final private Set<String> nonTerminals;
    final private Set<String> terminals;

    ParsingTable(
            List<NonTerminalSymbol> nonTerminals,
            List<TerminalSymbol> terminals,
            // List of tuple<NonTerminal, Terminal, Rule>
            List<ParsingTableEntry> entries
    ) {

        List<String> nonTerminalStrList = nonTerminals
                .stream()
                .map(Token::getSymbol)
                .toList();

        List<String> terminalStrList = terminals
                .stream()
                .map(Token::getSymbol)
                .toList();

        this.nonTerminals = new HashSet<String>(nonTerminalStrList);
        this.terminals = new HashSet<String>(terminalStrList);
        this.table = new HashMap<String, HashMap<String, Rule>>();
        // Build default table (all empty)
        for(String nt: this.nonTerminals) {
            this.table.put(nt, new HashMap<String, Rule>());
        }
        this.buildFromEntryList(entries);
    }

    private void buildFromEntryList(List<ParsingTableEntry> entries) {
        for(ParsingTableEntry entry: entries) {
            HashMap<String, Rule> row = this.table.get(entry.nonTerminal());
            row.put(entry.terminal(), entry.rule());
        }
    }
    public Optional<Rule> at(String nonTerminal, String terminal) {
        if (!this.isNonTerminal(nonTerminal) || !this.isTerminal(terminal)) {
            return Optional.empty();
        }
        var hit = table.get(nonTerminal).get(terminal);
        if(hit == null) {
            return Optional.empty();
        }

        return Optional.of(hit);
    }

    public boolean isNonTerminal(String nonTerminal) {
        return nonTerminals.contains(nonTerminal);
    }

    public boolean isTerminal(String terminal) {
        return terminals.contains(terminal);
    }
}

record ParsingTableEntry(String nonTerminal, String terminal, Rule rule) {
}