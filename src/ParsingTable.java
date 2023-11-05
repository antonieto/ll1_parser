import java.util.*;

/**
 * A parsing table that returns a Rule if given at the intersection of token i, j.
 */
public class ParsingTable {
    // Use a Map of Maps
    private HashMap<YYToken, HashMap<YYToken, Rule>> map;
    ParsingTable(HashMap<YYToken, HashMap<YYToken, Rule>> map) {
        this.map = map;
    }
    static ParsingTableBuilder builder(List<YYToken> tokenList) {
        return new ParsingTableBuilder(tokenList);
    }

    public boolean has(YYToken token) {
        return false;
    }
    public Optional<Rule> at(YYToken nonTerminal, YYToken terminal) {
        return Optional.empty();
    }

}

record ParsingTableEntry(String nonTerminal, String terminal, Rule rule) {
    @Override
    public int hashCode() {
        return Objects.hash(nonTerminal, terminal);
    }
}

class ParsingTableBuilder {
    final private Set<ParsingTableEntry> entries;

    // Map to quickly get a token based on a symbol
    final private HashMap<String, YYToken> symbolMap;

    ParsingTableBuilder(List<YYToken> tokenList) {
        symbolMap = new HashMap<>();
        for(YYToken token: tokenList) {
            symbolMap.put(token.toString(), token);
        }
        entries = new HashSet<>();
    }

    public ParsingTableBuilder add(String nonTerminal, String terminal, String production) throws GrammarException {
        // 1. Need to verify the symbols in the production (check that they belong in the token set).
        String[] productionTokens = production.trim().split(" ");
        List<YYToken> tokens = new ArrayList<>();
        for(String productionToken: productionTokens) {
            YYToken gotten = symbolMap.get(productionToken);
            if(gotten == null) {
                throw new GrammarException("Production rule has undefined symbol: " + productionToken);
            }
            tokens.add(gotten);
        }

        ParsingTableEntry entry = new ParsingTableEntry(
                nonTerminal, terminal, new Rule(nonTerminal, tokens)
        );
        if (entries.contains(entry)) {
            System.out.println("Warning: entry is being overwritten");
        }
        this.entries.add(entry);
        return this;
    }

    public ParsingTable build() {
        HashMap<YYToken, HashMap<YYToken, Rule>> map = new HashMap<>();
        for(ParsingTableEntry entry: entries) {
            YYToken rowKey = new YYToken(entry.nonTerminal(), TokenType.NON_TERMINAL);
            YYToken columnKey = new YYToken(entry.terminal(), TokenType.TERMINAL);
            var row = map.get(rowKey);
            if(row == null) {
                row = map.put(rowKey, new HashMap<>());
            }
            assert row != null;
            row.put(columnKey, entry.rule());
        }
        return new ParsingTable(map);
    }
}

class GrammarException extends Exception {
    private final String message;
    GrammarException(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}