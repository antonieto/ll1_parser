import java.text.ParseException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ParseException, GrammarException {
        // Declare all tokens (non-terminals and terminals)
        Token[] tokenList = {
                Token.nonTerminal("S"),
                Token.nonTerminal("A"),
                Token.nonTerminal("B"),
                Token.nonTerminal("F"),
                Token.nonTerminal("R"),
                Token.nonTerminal("P"),
                Token.nonTerminal("Q"),
                Token.terminal("+"),
                Token.terminal("("),
                Token.terminal(")"),
                Token.terminal(","),
                Token.terminal("f"),
                Token.terminal("n"),
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

        Token[] test = {
                Token.terminal("("),
                Token.terminal("f"),
                Token.terminal("("),
                Token.terminal("n"),
                Token.terminal(")"),
                Token.terminal("+"),
        };

        parser.parse(test);
        System.out.println("Parsed successfully!");
    }
}
