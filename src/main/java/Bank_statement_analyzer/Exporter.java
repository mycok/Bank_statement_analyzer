package Bank_statement_analyzer;

@FunctionalInterface
public interface Exporter {
    String export(final SummaryStats summaryStatistics);
}