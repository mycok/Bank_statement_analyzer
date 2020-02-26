package Bank_statement_analyzer;

public class CSVSyntaxException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CSVSyntaxException(String errMessage, Throwable err) {
        super(errMessage, err);
    }
}