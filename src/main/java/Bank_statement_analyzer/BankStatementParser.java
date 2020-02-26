package Bank_statement_analyzer;

import java.util.List;

/*
interfaces:
- Interfaces in java serve as contracts
- Any object / class that implements an interface is said to have honoured a given contract
- Implementing an interface requires that the implementing class / object provide or overide the abstract methods declared in the interface
- failure to fully honor the contract results into an execption

Best Practices:
- Declare light weight interfaces:
    polluting an interface with alot of abstract methods could lead to what they call a GOD interface.
    GOD interfaces pollute implementing objects with unnecessary method implementation since its required
    that all implementing objects provide implementations for all the declared abstract methods in an interface.
    any chang in the interface means all concrete implementations have to be updated as well to support the change.The more operations you add,
    the more likely changes will happen, increasing the scope for potential problems down the line
*/

public interface BankStatementParser {
    BankTransaction parseLineFrom(String line);
    List<BankTransaction> parseLinesFrom(List<String> lines);
}