package ua.nykyforov.geoip.dbip.repository;


import ua.nykyforov.geoip.dbip.api.GeoEntity;
import ua.nykyforov.geoip.dbip.model.GeoAttributes;

import java.net.InetAddress;

/**
 * Abstraction for repository. For instance, a repository can be a TreeMap, or Redis.
 *
 * @author Ankush Sharma
 */
public interface DbIpRepository {

    GeoEntity get(InetAddress inetAddress);

    void save(GeoAttributes geoAttributes);

}
