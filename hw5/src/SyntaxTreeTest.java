import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SyntaxTreeTest {

    String[] inputs = {
            "10 2 +",
            "1 20 + 31 49 + *",
            "1 2 + 3 4 2 / 5 * + *"
    };

    String expectedTrees[][] = {
            { "10", "+", "2"},
            { "1", "+", "20", "*", "31", "+", "49" },
            { "1", "+", "2", "*", "3", "+", "4", "/", "2", "*", "5" }
    };

    String[] expectedInfixes = {
            "(10 + 2)",
            "((1 + 20) * (31 + 49))",
            "((1 + 2) * (3 + ((4 / 2) * 5)))"
    };

    String[] expectedPrefixes = {
            "+ 10 2",
            "* + 1 20 + 31 49",
            "* + 1 2 + 3 * / 4 2 5"
    };

    String[] expectedResults = {
            "12.0",
            "1680.0",
            "39.0"
    };

    String[] expectedIndents = {
            "+\n"
          + "  10\n"
          + "  2\n",

            "*\n"
          + "  +\n"
          + "    1\n"
          + "    20\n"
          + "  +\n"
          + "    31\n"
          + "    49\n",

            "*\n"
          + "  +\n"
          + "    1\n"
          + "    2\n"
          + "  +\n"
          + "    3\n"
          + "    *\n"
          + "      /\n"
          + "        4\n"
          + "        2\n"
          + "      5\n"
    };

    private <T> void checkIt(Iterable<Position<T>> iterable, List<T> expected) {
        List<T> nodes = new ArrayList<>();
        iterable.forEach(i -> nodes.add(i.getElement()));

        assertEquals(expected, nodes);
    }

    SyntaxTree trees[] = new SyntaxTree[3];

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 3; i++) {
            trees[i] = buildSyntaxTree(inputs[i].split(" "));
        }
    }

    @Test
    void should_build_syntax_tree() {

        for (int i = 0; i < 3; i++) {
            SyntaxTree tree = SyntaxTree.buildSyntaxTree(inputs[i].split(" "));
            checkIt(tree.inOrder(), Arrays.stream(expectedTrees[i]).collect(Collectors.toList()));
        }
    }

    @Test
    void should_fully_parenthesize_infix() {
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedInfixes[i], trees[i].parenthesize());
        }
    }

    @Test
    void should_evaluate_expression() {
        for (int i = 0; i < 3; i++) {
            assertEquals(Double.valueOf(expectedResults[i]), trees[i].evaluate(), 0.1);
        }
    }

    @Test
    void should_convert_to_prefix() {
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedPrefixes[i], trees[i].toPrefix());
        }
    }

    @Test
    void should_print_indented_tree() {
        for (int i = 0; i < 3; i++) {
            assertEquals(expectedIndents[i], trees[i].indentSyntaxTree());
        }
    }
}
