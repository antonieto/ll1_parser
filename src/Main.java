import java.util.List;
import java.util.Stack;

public class Main {
    public static Stack<String> st = new Stack<>();
    public static ParsingTable table = new ParsingTable(
            List.of(
                    new NonTerminalSymbol("S"),
                    new NonTerminalSymbol("A"),
                    new NonTerminalSymbol("B"),
                    new NonTerminalSymbol("F"),
                    new NonTerminalSymbol("R"),
                    new NonTerminalSymbol("P"),
                    new NonTerminalSymbol("Q")
            ),
            List.of(
                    new TerminalSymbol("+"),
                    new TerminalSymbol("("),
                    new TerminalSymbol(")"),
                    new TerminalSymbol(","),
                    new TerminalSymbol("f"),
                    new TerminalSymbol("$"),
                    new TerminalSymbol("n")
            ),
            List.of(
                    new ParsingTableEntry("S", "(", new Rule("S", "A$")),
                    new ParsingTableEntry("S", "f", new Rule("S", "A$")),
                    new ParsingTableEntry("S", "n", new Rule("S", "A$")),
                    new ParsingTableEntry("A", "(", new Rule("A", "FB")),
                    new ParsingTableEntry("A", "f", new Rule("A", "FB")),
                    new ParsingTableEntry("A", "n", new Rule("A", "FB")),
                    new ParsingTableEntry("B", "+", new Rule("B", "+FB")),
                    new ParsingTableEntry("B", ")", new Rule("B", "\0")),
                    new ParsingTableEntry("B", "$", new Rule("B", "\0")),
                    new ParsingTableEntry("F", "(", new Rule("F", "(A)")),
                    new ParsingTableEntry("F", "f", new Rule("F", "f(R")),
                    new ParsingTableEntry("F", "n", new Rule("F", "n")),
                    new ParsingTableEntry("R", "(", new Rule("R", "P)")),
                    new ParsingTableEntry("R", ")", new Rule("R", ")")),
                    new ParsingTableEntry("R", "f", new Rule("R", "P)")),
                    new ParsingTableEntry("R", "n", new Rule("R", "P)")),
                    new ParsingTableEntry("P", "(", new Rule("P", "AQ")),
                    new ParsingTableEntry("P", "f", new Rule("P", "AQ")),
                    new ParsingTableEntry("P", "n", new Rule("P", "AQ")),
                    new ParsingTableEntry("Q", ")", new Rule("Q", "\0")),
                    new ParsingTableEntry("Q", ",", new Rule("Q", ",A"))
            )
    );

    public static void main(String[] args) {
        // 1. Init stack (add $, S)
        st.push("S");

        String test = "(f($";
        int charPointer = 0;

        while (!st.isEmpty()) {
            String current = String.valueOf(test.charAt(charPointer));
            // Top of stack
            String top = st.peek();
            if (table.isTerminal(top) || top.equals("$")) {
                if (!top.equals(current)) {
                    System.out.println("ERROR!");
                    return;
                }
                st.pop();
                charPointer++;
            }
            else if (table.isNonTerminal(top)) {
                var rule = table.at(top, current);
                if (rule.isEmpty()) {
                    System.out.println("ERROR here!");
                    return;
                }
                st.pop();
                String production = rule.get().getProduction();
                for (int i = production.length() - 1; i >= 0; --i) {
                    st.push(String.valueOf(production.charAt(i)));
                }
            } else {
                System.out.println("symbol not found");
                return;
            }
        }
        System.out.println("Sequence processed successfully!");
    }
}
