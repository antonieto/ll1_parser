import java.text.ParseException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ParseException, GrammarException {
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
                YYToken.terminal("n"),
        };
         ParsingTable table = ParsingTable.builder(Arrays.asList(tokenList))
                .add("S", "(", "A $")
                .add("S", "f", "A $")
                .add("S", "n", "A $")

                .add("A", "(", "F B")
                .add("A", "f", "F B")
                .add("A", "n", "F B")

                .add("B", "+", "+ F B")
                .add("B", ")", "EPSILON")
                .add("B", "$", "EPSILON")
                 .add("B", ",", "EPSILON")

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

                .add("Q", ")", "EPSILON")
                .add("Q", ",", ", A")
                .build();

        assert table != null;

        Parser parser = new Parser(
                table
        );

        YYToken[] test = {
                YYToken.terminal("("),
                YYToken.terminal("f"),
                YYToken.terminal("("),
                YYToken.terminal("n"),
                YYToken.terminal(")"),
                YYToken.terminal("+"),
        };

        parser.parse(test);
        System.out.println("Parsed successfully!");
    }
}
