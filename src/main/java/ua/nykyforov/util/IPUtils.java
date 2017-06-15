package ua.nykyforov.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Objects;

/**
 * @author Serhii Nykyforov
 */
public class IPUtils {

    public static BigInteger ipv6ToBigInteger(final InetAddress inetAddress) {
        Objects.requireNonNull(inetAddress, "inetAddress");
        final byte[] bytes = inetAddress.getAddress();
        return new BigInteger(1, bytes);
    }

}
