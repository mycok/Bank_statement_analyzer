package Bank_statement_analyzer;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Validate {
    /**
     * 
     * @param date
     * @param amount
     * @param description
     * @return
     */
    public Notifications validate(final String date, final String amount, final String description) {
        final Notifications notifications = new Notifications();

        if (description.length() > 100) {
            notifications.addError("description is too long");
        }
        // parse operations are most likely to fail so its best practice to wrap them in a try / catch statements
        try {
            if (LocalDate.parse(date).isAfter(LocalDate.now())) {
                notifications.addError("date cannot be in the future");
            }
        } catch (DateTimeException e) {
            notifications.addError("invalid format for date");
        }
        
        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            notifications.addError("Invalid format for amount");
        }

        return notifications;
    }
}