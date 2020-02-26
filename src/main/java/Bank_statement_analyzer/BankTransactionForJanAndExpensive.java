package Bank_statement_analyzer;

import java.time.Month;

/*
To use the BankFilterFilter interface with the findTransactions method,
we need to create a class to implement the test interface method and then
pass an instance of this class to the findTransactions method along with the test implementation
 */

public class BankTransactionForJanAndExpensive implements BankTransactionFilter {
    @Override
    public boolean test(final BankTransaction bankTransaction) {
        return bankTransaction.getDate().getMonth() == Month.JANUARY 
        && bankTransaction.getAmount() >= 1_000;
    }
}