package ua.nykyforov.geoip.dbip.importer;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.net.InetAddresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.geoip.dbip.CountryResolver;
import ua.nykyforov.geoip.dbip.model.GeoAttributes;
import ua.nykyforov.geoip.dbip.model.GeoAttributesImpl;
import ua.nykyforov.geoip.dbip.parser.CsvParser;
import ua.nykyforov.geoip.dbip.parser.CsvParserImpl;
import ua.nykyforov.geoip.dbip.repository.DbIpRepository;
import ua.nykyforov.geoip.dbip.repository.JavaMapDbIpRepositoryImpl;
import ua.nykyforov.util.GzipUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Singleton class responsible for loading the entire file into the JVM.
 */
public final class ResourceImporter {

    private static final Logger logger = LoggerFactory.getLogger(ResourceImporter.class);
    private final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();
    private final CsvParser csvParser = CsvParserImpl.getInstance();
    private static ResourceImporter instance = null;

    private Interner<String> interner = Interners.newWeakInterner();

    private ResourceImporter() {
    }

    public static ResourceImporter getInstance() {
        if (instance == null) {
            return new ResourceImporter();
        }
        return instance;
    }

    /**
     * Loads the file into JVM, reading line by line.
     * Also transforms each line into a GeoEntity object,
     * saving the object into the repository.
     *
     * @param file The dbip-country-latest.csv.gz file as a File object.
     */
    public void load(final File file) {
        checkArgument(GzipUtils.isGzipped(file), "Not a gzip file");
        String[] array;

        try (final InputStream fis = new FileInputStream(file);
             final InputStream gis = new GZIPInputStream(fis);
             final Reader decorator = new InputStreamReader(gis, StandardCharsets.UTF_8);
             final BufferedReader reader = new BufferedReader(decorator);
        ) {
            logger.debug("Reading dbip data from {}", file.getName());
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                array = csvParser.parseRecord(line);

                final GeoAttributes geoAttributes = GeoAttributes.newBuilder()
                        .withStartInetAddress(InetAddresses.forString(array[0]))
                        .withEndInetAddress(InetAddresses.forString(array[1]))
                        .withCountryCode(array[2])
                        .withCountry(CountryResolver.resolveToFullName(array[2]))
                        .build();
                repository.save(geoAttributes);
                if (i % 100000 == 0) {
                    logger.debug("Loaded {} entries", i);
                }
            }
        } catch (final Exception e) {

            throw new RuntimeException(e);
        }
    }
}
