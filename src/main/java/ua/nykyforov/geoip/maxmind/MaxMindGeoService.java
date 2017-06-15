package ua.nykyforov.geoip.maxmind;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.record.Country;
import ua.nykyforov.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.util.Objects.requireNonNull;

/**
 * @author Serhii Nykyforov
 */
public class MaxMindGeoService {

    private final DatabaseReader reader;

    private MaxMindGeoService(DatabaseReader reader) {
        this.reader = requireNonNull(reader, "reader");
    }

    public static MaxMindGeoService fromResource(String filename) {
        InputStream database = IOUtils.getStreamFromResource(filename);
        DatabaseReader reader = null;
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            throw new RuntimeException("Could not build reader", e);
        }
        return new MaxMindGeoService(reader);
    }

    public Country findCountryByIp(String rawIp) {
        InetAddress ipAddress = toInetAddress(rawIp);
        return findCountryByIp(ipAddress);
    }

    private InetAddress toInetAddress(String raw) {
        requireNonNull(raw, "ip");
        try {
            return InetAddress.getByName(raw);
        } catch (UnknownHostException e) {
            String msg = String.format("Could not build InetAddress from raw string: %s", raw);
            throw new RuntimeException(msg, e);
        }
    }

    public Country findCountryByIp(InetAddress ip) {
        requireNonNull(ip, "ip");
        try {
            return reader.country(ip).getCountry();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
