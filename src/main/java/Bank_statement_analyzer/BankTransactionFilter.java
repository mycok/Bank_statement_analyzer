package Bank_statement_analyzer;

// An interface that contains only one abstract method is called a functional interface
@FunctionalInterface
public interface BankTransactionFilter {
    boolean test(BankTransaction bankTransaction);
}