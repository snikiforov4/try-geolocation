package ua.nykyforov.util;

import com.google.common.base.Throwables;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;

/**
 * @author Serhii Nykyforov
 */
public final class GzipUtils {
    private GzipUtils() {
    }

    /**
     * Checks if the file is gzipped.
     */
    public static boolean isGzipped(final File file) {
        int magic = 0;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return magic == GZIPInputStream.GZIP_MAGIC;
    }
}
