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

        final Path path = Path.of(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);
        final SummaryStats summaryStatistics = bankStatementProcessor.generateTransactionStatistics();

        displayTransactionSummary(bankStatementProcessor);
        System.out.println(exporter.export(summaryStatistics));
    }
    
    private static void displayTransactionSummary(final BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transactions is " + bankStatementProcessor.computeTotalAmount());
        System.out.println("Transactions in january " + bankStatementProcessor.computeMonthlyTotal(Month.JANUARY));
        System.out.println("Transactions in january " + bankStatementProcessor.computeMonthlyTotal(Month.FEBRUARY));
        System.out.println("Total salary recieved is " + bankStatementProcessor.computeCategoryTotal("Salary"));
        // we call bankSatementProcessor.findtransactions and pass it a new instance of BankTransactionForJanAndExpensive
        // this returns the most expesive transaction for the specified month of type BankTransaction
        System.out.println("The most expensive month is " + bankStatementProcessor.findTransactions(new BankTransactionForJanAndExpensive()));
        System.out.println("The most expensive month is " + bankStatementProcessor.findTransactionsForMonth(Month.JANUARY));
        System.out.println("The most expensive month is " + bankStatementProcessor.findTransactionsGreaterOrEqual(1000));
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