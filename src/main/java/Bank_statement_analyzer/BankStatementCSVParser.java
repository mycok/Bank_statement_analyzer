package Bank_statement_analyzer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Here we are encapsulating the parsing functionality in a BankStatementCSVParser class
// We also achieve a high cohesion rate with this class
public class BankStatementCSVParser implements BankStatementParser {
    private static final DateTimeFormatter DATE_PAATTERN
    = DateTimeFormatter.ofPattern("dd-MM-yyy");

    public BankTransaction parseLineFrom(final String line) {
        final int EXPECTED_ATTRIBUTES_LENGTH = 3;
        final String[] columns = line.split(",");
        // validate csv data columns
        if ( columns.length < EXPECTED_ATTRIBUTES_LENGTH) {
            // throw or return a CSVErrorException
        }

        // validate csv column data values
        final Notifications notifications = new Validate().validate(columns[0], columns[1], columns[2]);

        if (notifications.hasErrors()) {
            //  return and display the errors from notifications class
        }
        // parse the column values to the match the expected BankTransaction variable types
        final LocalDate date = LocalDate.parse(columns[0], DATE_PAATTERN);
        final var amount = Double.parseDouble(columns[1]);
        final var description = columns[2];
        // return a BankTransaction object
        return new BankTransaction(date, amount, description);
    }

    public List<BankTransaction> parseLinesFrom(final List<String> lines) {
        final List<BankTransaction> bankTransactions = new ArrayList<>();

        for(final String line: lines) {
            bankTransactions.add(parseLineFrom(line));
        }

        return bankTransactions;
    }
}