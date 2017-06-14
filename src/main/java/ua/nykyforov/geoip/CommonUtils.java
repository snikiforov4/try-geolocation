package ua.nykyforov.geoip;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static com.google.common.base.Throwables.throwIfUnchecked;
import static java.util.Objects.requireNonNull;

/**
 * @author Serhii Nykyforov
 */
public class CommonUtils {

    public static File getResourceFile(String filename) {
        ClassLoader classLoader = CommonUtils.class.getClassLoader();
        URL url = requireNonNull(classLoader.getResource(filename),
                "No url with given name");
        URI uri = getURI(url);
        return new File(uri);
    }

    private static URI getURI(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
