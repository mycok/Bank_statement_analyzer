package Bank_statement_analyzer;

import java.util.ArrayList;
import java.util.List;

public class Notifications {
    private final List<String> errors = new ArrayList<>();

    public void addError(final String errMessage) {
        errors.add(errMessage);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public String errorMessage() {
        return errors.toString();
    }
    
    public List<String> getErrors() {
        return errors;
    }
}