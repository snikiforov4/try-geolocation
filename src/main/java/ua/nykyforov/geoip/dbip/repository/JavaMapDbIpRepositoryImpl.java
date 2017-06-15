package ua.nykyforov.geoip.dbip.repository;

import com.google.common.net.InetAddresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nykyforov.geoip.dbip.api.GeoEntity;
import ua.nykyforov.geoip.dbip.model.GeoAttributes;
import ua.nykyforov.util.IPUtils;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Singleton that uses a TreeMap as repository.
 *
 * @author Ankush Sharma
 */
public final class JavaMapDbIpRepositoryImpl implements DbIpRepository {
    private static final Logger logger = LoggerFactory.getLogger(JavaMapDbIpRepositoryImpl.class);

    private static JavaMapDbIpRepositoryImpl instance = null;

    private JavaMapDbIpRepositoryImpl() {
    }

    public static JavaMapDbIpRepositoryImpl getInstance() {
        if (instance == null) {
            return new JavaMapDbIpRepositoryImpl();
        }
        return instance;
    }

    private static final TreeMap<Integer, GeoEntity> IPV4_REPOSITORY = new TreeMap<>();
    private static final TreeMap<BigInteger, GeoEntity> IPV6_REPOSITORY = new TreeMap<>();

    /**
     * Lookup GeoEntity
     */
    @Override
    public GeoEntity get(final InetAddress inetAddress) {
        Objects.requireNonNull(inetAddress, "inetAddress");
        if (inetAddress instanceof Inet4Address) {
            final Integer startIpNum = InetAddresses.coerceToInteger(inetAddress);

            return IPV4_REPOSITORY.floorEntry(startIpNum) == null ? null
                    : IPV4_REPOSITORY.floorEntry(startIpNum).getValue();
        } else {
            final BigInteger startIpBigInt = IPUtils.ipv6ToBigInteger(inetAddress);
            return IPV6_REPOSITORY.floorEntry(startIpBigInt) == null ? null
                    : IPV6_REPOSITORY.floorEntry(startIpBigInt).getValue();
        }
    }

    /**
     * Save GeoEntity for an InetAddress
     *
     * @param geoAttributes The attributes to be saved . Contains the attributes that will be needed
     *                      as key and value to be put inside the TreeMap.
     */
    @Override
    public void save(final GeoAttributes geoAttributes) {
        Objects.requireNonNull(geoAttributes, "geoAttributes");
        final InetAddress startInetAddress = geoAttributes.getStartInetAddress();
        final InetAddress endInetAddress = geoAttributes.getEndInetAddress();
        final GeoEntity geoEntity = geoAttributes.getGeoEntity();

        if (startInetAddress instanceof Inet6Address
                && endInetAddress instanceof Inet6Address) {
            final BigInteger startIpBigInt = IPUtils.ipv6ToBigInteger(startInetAddress);
            IPV6_REPOSITORY.put(startIpBigInt, geoEntity);
        } else if (startInetAddress instanceof Inet4Address
                && endInetAddress instanceof Inet4Address) {
            final Integer startIpNum = InetAddresses.coerceToInteger(startInetAddress);
            IPV4_REPOSITORY.put(startIpNum, geoEntity);
        } else {
            //Well, this case should never happen. Maybe I'll throw in an exception later.
            logger.warn("This shouldn't ever happen");
        }
    }

    public static TreeMap<Integer, GeoEntity> getIpv4Repository() {
        return IPV4_REPOSITORY;
    }
}
