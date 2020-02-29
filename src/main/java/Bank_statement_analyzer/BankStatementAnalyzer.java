package Bank_statement_analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;
import java.util.List;

// perfect example of decoupling: here we have used the BankStatementParser interface to provide parsing fuctionality as opposed to using a specific parser class.
// This means that changes in the various parser classes has no efffect to the functioning of this class
public class BankStatementAnalyzer {
    private static final String RESOURCES = "src/main/resources/";

    public void analyze(
        final String fileName,
        final BankStatementParser bankStatementParser,
        final Exporter exporter
        ) throws IOException {
        // use the provided fileName to get the location of the file
        final Path path = Path.of(RESOURCES + fileName);
        // after locating the file using the path, we extract all lines from it
        final List<String> lines = Files.readAllLines(path);
        // we then create bank transaction objects by parsing each line from the file
        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
        // now that we have our BankTransactions, we can process them using our BankStatementProcessor
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);
        // after processing the transactions, we can generate useful statistics such as average, max, min etc
        final SummaryStats summaryStatistics = bankStatementProcessor.generateTransactionStatistics();
        // we then finally display statistics
        displayTransactionSummary(bankStatementProcessor);
        System.out.println(exporter.export(summaryStatistics));
    }
    
    private static void displayTransactionSummary(final BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transactions is " + bankStatementProcessor.computeTotalAmount());
        System.out.println("Transactions in january " + bankStatementProcessor.computeMonthlyTotal(Month.JANUARY));
        System.out.println("Transactions in febraury " + bankStatementProcessor.computeMonthlyTotal(Month.FEBRUARY));
        System.out.println("Total salary recieved is " + bankStatementProcessor.computeCategoryTotal("Salary"));
        // we call bankSatementProcessor.findtransactions and pass it a new instance of BankTransactionForJanAndExpensive
        // this returns the most expesive transaction for the specified month of type BankTransaction
        // System.out.println("The most expensive month is " + bankStatementProcessor.findTransactions(new BankTransactionForJanAndExpensive()));
        System.out.println("The transactions for january are " + bankStatementProcessor.findTransactionsForMonth(Month.JANUARY));
        System.out.println("Here are transactions greater or equal to $1000" + bankStatementProcessor.findTransactionsGreaterOrEqual(1000));
        // implementing the BankTransactionSummarizer using an anonymous class
        // this strategy solves the need to create custom class implementation whenever you need to query by a new parater
        // however it also introduces unnecessary boiler plate code which is solved by using lambda expreessions
        System.out.println("The most expensive month is " + bankStatementProcessor.summarizeTransactions(
            new BankTransactionSummarizer(){
                @Override
                public double summarize(BankTransaction bankTransaction, double accumulator) {
                    return bankTransaction.getAmount();
                }
            }
        ));
        // this should return a result from the anonymous class implementation
        System.out.println("The total for all transactions is " + bankStatementProcessor.computeTotalAmount());
    }
}