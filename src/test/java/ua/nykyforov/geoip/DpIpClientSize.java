package ua.nykyforov.geoip;

import org.ehcache.sizeof.SizeOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.geoip.dbip.api.DbIpClient;
import ua.nykyforov.geoip.dbip.repository.JavaMapDbIpRepositoryImpl;

import java.io.IOException;

import static ua.nykyforov.util.IOUtils.getResourceFile;

/**
 * @author Serhii Nykyforov
 */
class DpIpClientSize {

    private static final Logger LOG = LoggerFactory.getLogger("Geo");

    private static void checkSize() throws IOException {
        DbIpClient client = new DbIpClient(getResourceFile(Global.DB_IP_DATABASE_NAME));

        LOG.info("Client size = {}", SizeOf.newInstance().deepSizeOf(client));
        LOG.info("IPv4 repository size = {}", SizeOf.newInstance().deepSizeOf(JavaMapDbIpRepositoryImpl.getIPv4Repository()));
        LOG.info("IPv6 repository size = {}", SizeOf.newInstance().deepSizeOf(JavaMapDbIpRepositoryImpl.getIPv6Repository()));
    }

    public static void main(String[] args) throws IOException {
        checkSize();
    }
}
