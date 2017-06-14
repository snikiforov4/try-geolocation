package ua.nykyforov.geoip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.record.Country;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.util.Objects.requireNonNull;

/**
 * @author Serhii Nykyforov
 */
public class GeoService {

    private final DatabaseReader reader;

    private GeoService(DatabaseReader reader) {
        this.reader = requireNonNull(reader, "reader");
    }

    public static GeoService fromResourceFile(String filename) {
        File database = CommonUtils.getResourceFile(filename);
        DatabaseReader reader = null;
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            throw new RuntimeException("Could not build reader", e);
        }
        return new GeoService(reader);
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
