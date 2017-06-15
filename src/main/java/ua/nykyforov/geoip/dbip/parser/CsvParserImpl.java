package ua.nykyforov.geoip.dbip.parser;

import com.google.common.base.Strings;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

public final class CsvParserImpl implements CsvParser {

    private static CsvParserImpl parser = null;

    private CsvParserImpl() {
    }

    public static CsvParserImpl getInstance() {
        if (parser == null) {
            return new CsvParserImpl();
        }
        return parser;
    }

    @Override
    public String[] parseRecord(final String csvRecord) {
        checkArgument(!Strings.isNullOrEmpty(csvRecord), "passed csvRecord null or empty");

        return Arrays.stream(csvRecord.split(","))
                .map(str -> str.replace("\"", "").trim())
                .toArray(String[]::new);
    }

}
