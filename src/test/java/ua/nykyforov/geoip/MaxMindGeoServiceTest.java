package ua.nykyforov.geoip;

import com.maxmind.geoip2.record.Country;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ua.nykyforov.geoip.maxmind.MaxMindGeoService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Serhii Nykyforov
 */
class MaxMindGeoServiceTest {

    private static MaxMindGeoService geoService;

    @BeforeAll
    static void init() throws IOException {
         geoService = MaxMindGeoService.fromResource(Global.MAXMIND_DATABASE_NAME);
    }

    @Nested
    class FindCountryByIP {

        @Test
        void simpleTest() {
            String ip = "195.140.160.233";
            Country country = geoService.findCountryByIp(ip);

            assertEquals("Ukraine", country.getName());
            assertEquals("UA", country.getIsoCode());
        }

    }
}