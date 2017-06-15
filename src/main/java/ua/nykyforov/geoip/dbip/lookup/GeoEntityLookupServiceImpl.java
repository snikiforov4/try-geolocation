package ua.nykyforov.geoip.dbip.lookup;


import ua.nykyforov.geoip.dbip.api.GeoEntity;
import ua.nykyforov.geoip.dbip.repository.DbIpRepository;
import ua.nykyforov.geoip.dbip.repository.JavaMapDbIpRepositoryImpl;

import java.net.InetAddress;

import static java.util.Objects.requireNonNull;

/**
 * Singleton class that resolves ip to Location info.
 *
 * @author Ankush Sharma
 */
public final class GeoEntityLookupServiceImpl implements GeoEntityLookupService {

    private static final String UNKNOWN = "Unknown";

    private final DbIpRepository repository = JavaMapDbIpRepositoryImpl.getInstance();

    private static GeoEntityLookupServiceImpl instance = null;

    private GeoEntityLookupServiceImpl() {
    }

    public static GeoEntityLookupServiceImpl getInstance() {
        if (instance == null) {
            return new GeoEntityLookupServiceImpl();
        }
        return instance;
    }

    @Override
    public GeoEntity lookup(final InetAddress inetAddress) {
        requireNonNull(inetAddress, "inetAddress");
        GeoEntity geoEntity = repository.get(inetAddress);
        if (geoEntity == null) {
            geoEntity = new GeoEntity
                    .Builder()
                    .withCountry(UNKNOWN)
                    .withCountryCode(UNKNOWN)
                    .build();
        }
        return geoEntity;
    }
}
