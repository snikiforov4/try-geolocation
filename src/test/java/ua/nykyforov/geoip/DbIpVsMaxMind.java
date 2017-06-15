package ua.nykyforov.geoip;

import com.maxmind.geoip2.record.Country;
import com.sun.istack.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.geoip.dbip.api.DbIpClient;
import ua.nykyforov.geoip.dbip.api.GeoEntity;
import ua.nykyforov.geoip.maxmind.MaxMindGeoService;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static ua.nykyforov.util.IOUtils.getResourceFile;

/**
 * @author Serhii Nykyforov
 */
public class DbIpVsMaxMind {

    private static final Logger LOG = LoggerFactory.getLogger("Geo");
    private static final int THRESHOLD = 1_000;
    private static final String UNKNOWN_COUNTRY = "Unknown";
    private static MaxMindGeoService mmClient;
    private static DbIpClient dbIpClient;

    public static void main(String[] args) {
        dbIpClient = new DbIpClient(getResourceFile(Global.DB_IP_DATABASE_NAME));
        mmClient = MaxMindGeoService.fromResource(Global.MAX_MIND_DATABASE_NAME);
        int cntOfCountriesKnownForMM = 0;
        int cntOfCountriesKnownForDbIp = 0;
        for (int i = 0; i < THRESHOLD; i++) {
            String ipAddress = genRandomIpAddress();

            Country mmCountry = safelyGetCountryFromMaxMind(ipAddress);
            GeoEntity dbIpCountry = safelyGetCountryFromDbIp(ipAddress);

            String mmCountryName = mmCountry == null ? UNKNOWN_COUNTRY : mmCountry.getName();
            String mmCountryCode = mmCountry == null ? UNKNOWN_COUNTRY : mmCountry.getIsoCode();
            String dbIpCountryName = dbIpCountry == null ? UNKNOWN_COUNTRY : dbIpCountry.getCountry();
            String dbIpCountryCode = dbIpCountry == null ? UNKNOWN_COUNTRY : dbIpCountry.getCountryCode();

            if (isCountryDefined(mmCountryName)) {
                cntOfCountriesKnownForMM++;
            }
            if (isCountryDefined(dbIpCountryName)) {
                cntOfCountriesKnownForDbIp++;
            }
            if (!Objects.equals(mmCountryName, dbIpCountryName) && !Objects.equals(mmCountryCode, dbIpCountryCode)) {
                LOG.warn("{} maxMind: {}, dbIp: {}", ipAddress, mmCountryName, dbIpCountry);
            }

        }
        LOG.info("MaxMind knows countries: {} ({})", cntOfCountriesKnownForMM, THRESHOLD);
        LOG.info("DB-IP knows countries: {} ({})", cntOfCountriesKnownForDbIp, THRESHOLD);
    }

    @Nullable
    private static Country safelyGetCountryFromMaxMind(String ipAddress) {
        try {
            return mmClient.findCountryByIp(ipAddress);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Nullable
    private static GeoEntity safelyGetCountryFromDbIp(String ipAddress) {
        try {
            return dbIpClient.lookup(ipAddress);
        } catch (Exception ignored) {
        }
        return null;
    }

    private static String genRandomIpAddress() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        return String.valueOf(r.nextInt(0, 256)) + '.' +
                r.nextInt(0, 256) + '.' +
                r.nextInt(0, 256) + '.' +
                r.nextInt(0, 256);
    }

    private static boolean isCountryDefined(String country) {
        return country != null && !Objects.equals(country, UNKNOWN_COUNTRY);
    }

}
