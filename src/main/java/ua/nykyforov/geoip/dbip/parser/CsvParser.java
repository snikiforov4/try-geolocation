package ua.nykyforov.geoip.dbip.parser;

public interface CsvParser {
    String[] parseRecord(String csvRecord);
}
