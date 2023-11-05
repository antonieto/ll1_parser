import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        // Declare all tokens (non-terminals and terminals)
        YYToken[] tokenList = {
                YYToken.nonTerminal("S"),
                YYToken.nonTerminal("A"),
                YYToken.nonTerminal("B"),
                YYToken.nonTerminal("F"),
                YYToken.nonTerminal("R"),
                YYToken.nonTerminal("P"),
                YYToken.nonTerminal("Q"),
                YYToken.terminal("+"),
                YYToken.terminal("("),
                YYToken.terminal(")"),
                YYToken.terminal(","),
                YYToken.terminal("f"),
                YYToken.DOLLAR,
                YYToken.terminal("n"),
        };
        try {
            var table = ParsingTable.builder(Arrays.asList(tokenList))
                    .add("S", "(", "A $")
                    .add("S", "f", "A $")
                    .add("S", "n", "A $")

                    .add("A", "(", "F B")
                    .add("A", "f", "F B")
                    .add("A", "n", "F B")

                    .add("B", "+", "A $")
                    .add("B", ")", "A $")
                    .add("B", "$", "A $")

                    .add("F", "(", "( A )")
                    .add("F", "f", "f ( R")
                    .add("F", "n", "n")

                    .add("R", "(", "P )")
                    .add("R", ")", ")")
                    .add("R", "f", "P )")
                    .add("R", "n", "P )")

                    .add("P", "(", "A Q")
                    .add("P", "f", "A Q")
                    .add("P", "n", "A Q")

                    .add("Q", ")", "\0")
                    .add("Q", ",", ", A")
                    .build();
        } catch (GrammarException exc) {
            System.out.println("Invalid parsing table");
            System.out.println(exc.toString());
        }

    }
}
