package Bank_statement_analyzer;

import java.time.Month;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public SummaryStats generateTransactionStatistics() {
        // DoubleSummaryStatistics is a class from java.util.DoubleSummaryStatistics
        // DoubleSummaryStatistics class mostly recommended to be used with stream objects
        // it helps with statistical data reduction by computing and returning data values such as sum, max, min and average
        // all returned values are wrapped in a summaryStatistics object serving as an API with getter methods

        final DoubleSummaryStatistics doubleSummaryStatistics = bankTransactions.stream()
        .mapToDouble(BankTransaction::getAmount)
        .summaryStatistics();

        return new SummaryStats(
            doubleSummaryStatistics.getSum(),
            doubleSummaryStatistics.getMax(),
            doubleSummaryStatistics.getMin(),
            doubleSummaryStatistics.getAverage()
        );
    }
    /*we are using the open/closed principle from SOLID to make findTransactions method dynamic
    this means the method is open for extension but closed for modification.
    this behaviour is achieved through the use of functional interfaces, creating class that implement the interface methods,
    and the passing an instance of that class to the concerned function.

    it can also be archieved by passing of lambda expressions and annonymous class definations as arguments
    */

    /*
    cons for open/close principle:
    - reduces fragility of code by not changing existing code. (chnging existing code introduces bugs and failing tests)
    - promotes code reusability. (avoids code duplication)
    - promotes decoupling (better code mantainance)
     */


    // below is a method that serves as a base BankTransactionSummarizer interface implementation method since it takes the interface as an argument
    // we call this method and pass it a lambda expression with custom summarize implementation,
    // whenever we want to perform custom transaction summaries
    public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0;
        for (BankTransaction bankTransaction: bankTransactions) {
            result = bankTransactionSummarizer.summarize(bankTransaction, result);
        }

        return result;  
    }
    // java lambda functions / closures
    // they help us write concise code by eliminating the need to create an iterface implementation class
    // this is achieved by passing the interface implementation as a function argument with a custom implementation
    public double computeTotalAmount() {
        return summarizeTransactions((bankTransaction, acc) -> acc += bankTransaction.getAmount());
     }

    public double computeMonthlyTotal(final Month month) {
        return summarizeTransactions((bankTransaction, acc) -> 
        bankTransaction.getDate().getMonth() == month ? acc + bankTransaction.getAmount() : acc);
    }

    public double computeCategoryTotal(final String category) {
        return summarizeTransactions((bankTransaction, acc) -> 
            bankTransaction.getDescription().equals(category) ? acc + bankTransaction.getAmount() : acc);
    }

    // lambda expression implementations for BanktransactionsFilter interface
    public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
        final List<BankTransaction> bankTransactions = new ArrayList<>();
        for (final BankTransaction bankTransaction: bankTransactions) {
            if (bankTransactionFilter.test(bankTransaction)) {
                bankTransactions.add(bankTransaction);
            }
        }

        return bankTransactions;
    }

    public List<BankTransaction> findTransactionsForMonth(final Month month) {
        return findTransactions(bankTransaction -> bankTransaction.getDate().getMonth() == month);
    }

    // java lambda functions / closures
    public List<BankTransaction> findTransactionsGreaterOrEqual(final int amount) {
        return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
    }
}
