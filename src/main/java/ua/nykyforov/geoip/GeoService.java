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

    public static GeoService fromResourceFile(String filename) throws IOException {
        File database = CommonUtils.getResourceFile(filename);
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
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
            throw new RuntimeException("Could not build ip address", e);
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
