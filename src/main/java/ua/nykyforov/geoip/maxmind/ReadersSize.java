package ua.nykyforov.geoip.maxmind;

import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.util.CommonUtils;
import ua.nykyforov.util.ObjectSizeFetcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static ua.nykyforov.geoip.Global.MAXMIND_DATABASE_NAME;

/**
 * @author Serhii Nykyforov
 */
class ReadersSize {

    private static final Logger LOG = LoggerFactory.getLogger("Geo");

    private static void checkSizeUsingMemoryMode() throws IOException {
        InputStream database = CommonUtils.getStreamFromResource(MAXMIND_DATABASE_NAME);
        DatabaseReader reader = new DatabaseReader.Builder(database)
                .fileMode(Reader.FileMode.MEMORY)
                .build();

        LOG.info("Reader size with MEMORY mode = {}", ObjectSizeFetcher.getObjectSize(reader));
    }

    private static void checkSizeUsingMemoryMappedMode() throws IOException {
        File database = CommonUtils.getResourceFile(MAXMIND_DATABASE_NAME);
        DatabaseReader reader = new DatabaseReader.Builder(database)
                .fileMode(Reader.FileMode.MEMORY_MAPPED)
                .build();

        LOG.info("Reader size with MEMORY_MAPPED mode = {}", ObjectSizeFetcher.getObjectSize(reader));
    }

    public static void main(String[] args) throws IOException {
        checkSizeUsingMemoryMode();
        checkSizeUsingMemoryMappedMode();
    }
}
