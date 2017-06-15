package ua.nykyforov.geoip;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.geoip.dbip.api.DbIpClient;
import ua.nykyforov.geoip.dbip.api.GeoEntity;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.nykyforov.util.IOUtils.getResourceFile;

/**
 * @author Serhii Nykyforov
 */
class DbIpTest {

    private static final Logger LOG = LoggerFactory.getLogger("Geo");
    private static DbIpClient client;

    @BeforeAll
    static void init() throws IOException {
        File gzip = getResourceFile(Global.DB_IP_DATABASE_NAME);
        client = new DbIpClient(gzip);
    }

    @Nested
    class FindCountryByIP {

        @Test
        void simpleTest() {
            String ip = "195.140.160.233";
            GeoEntity entity = client.lookup(ip);
            LOG.info("{}", entity);

            assertEquals("Ukraine", entity.getCountry());
            assertEquals("UA", entity.getCountryCode());
        }

    }
}