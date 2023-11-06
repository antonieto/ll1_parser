import java.util.*;

/**
 * A parsing table that returns a Rule if given at the intersection of token i, j.
 */
public class ParsingTable {
    // Use a Map of Maps
    private final HashMap<YYToken, HashMap<YYToken, Rule>> map;
    private final HashMap<String, YYToken> symbolMap;
    ParsingTable(HashMap<YYToken, HashMap<YYToken, Rule>> map, HashMap<String, YYToken> symbolMap) {
        this.map = map;
        this.symbolMap = symbolMap;
    }
    static ParsingTableBuilder builder(List<YYToken> tokenList) {
        return new ParsingTableBuilder(tokenList);
    }

    public boolean has(YYToken token) {
        var gotten = symbolMap.get(token.toString());
        return gotten == null;
    }
    public Optional<Rule> at(YYToken nonTerminal, YYToken terminal) {
        var gotten = map.get(nonTerminal).get(terminal);
        if (gotten == null) {
            return Optional.empty();
        }
        return Optional.of(gotten);
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

        // Insert default symbols
        symbolMap.put(YYToken.EPSILON.toString(), YYToken.EPSILON);
        symbolMap.put(YYToken.DOLLAR.toString(), YYToken.DOLLAR);

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
                throw new GrammarException(String.format("Production rule %s has undefined symbol: %s", production, productionToken));
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
                map.put(rowKey, new HashMap<>());
            }
            row = map.get(rowKey);
            assert row != null;
            row.put(columnKey, entry.rule());
        }
        return new ParsingTable(map, symbolMap);
    }
}

