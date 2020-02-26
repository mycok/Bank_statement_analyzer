package Bank_statement_analyzer;

@FunctionalInterface
public interface BankTransactionSummarizer {
    double summarize(final BankTransaction bankTransaction, double accumulator);
}
