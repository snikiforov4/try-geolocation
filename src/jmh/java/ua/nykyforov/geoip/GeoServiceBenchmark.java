package ua.nykyforov.geoip;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="mailto:s.nikiforov@corp.nekki.ru">Sergey Nikiforov</a>
 */
@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput})
@Fork(1)
public class GeoServiceBenchmark {

    private GeoService geoService;

    @Setup
    public void prepare() {
        geoService = GeoService.fromResourceFile("GeoLite2-Country.mmdb");;
    }

    @Benchmark
    @Measurement(iterations = 5)
    @Warmup(iterations = 2)
    public void findingCountries() {
        geoService.findCountryByIp(genRandomIpAddress());
    }

    private String genRandomIpAddress() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int i = r.nextInt(0, 256);
        return "195.140.160." + i;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(".*" + GeoServiceBenchmark.class.getSimpleName() + ".*")
                .build();

        new Runner(options).run();
    }

}
