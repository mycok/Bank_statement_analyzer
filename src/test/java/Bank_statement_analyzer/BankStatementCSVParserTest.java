package Bank_statement_analyzer;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankStatementCSVParserTest {
    private final BankStatementParser statementParser = new BankStatementCSVParser();
    
    @Test
    public void shouldParseOneCorrectLine() throws Exception {
        /*
        1. You setup the context for your test.In this case a line to parse.
        2. You carry out an action.In this case,you parse the inputline.
        3. You specify assertions of the expected output.Here,you check that the date, amount, and description were parsed correctly.

        This three-stage pattern for setting up a unit test is often referred to as the Given- When-Then formula
         */
        final String line = "30-01-2019,-50,Apache";
        final BankTransaction result = statementParser.parseLineFrom(line);
        final BankTransaction expected = new BankTransaction(LocalDate.of(2019, Month.JANUARY, 30), -50, "Apache");

        final double tolerance = 0.0d;

        assertEquals(expected.getDate(), result.getDate());
        assertEquals(expected.getAmount(), result.getAmount(), tolerance);
        assertEquals(expected.getDescription(), result.getDescription());
    }
}