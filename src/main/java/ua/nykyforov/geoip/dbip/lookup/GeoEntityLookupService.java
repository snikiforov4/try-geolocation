package ua.nykyforov.geoip.dbip.lookup;

import ua.nykyforov.geoip.dbip.api.GeoEntity;

import java.net.InetAddress;

public interface GeoEntityLookupService {
    GeoEntity lookup(InetAddress inetAddress);
}
