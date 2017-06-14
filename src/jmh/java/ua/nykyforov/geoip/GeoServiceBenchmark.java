package ua.nykyforov.geoip;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="mailto:s.nikiforov@corp.nekki.ru">Sergey Nikiforov</a>
 */
public class GeoServiceBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        GeoService geoService = GeoService.fromResourceFile("GeoLite2-Country.mmdb");
    }

    @Benchmark
    public void findingCountries(BenchmarkState state) {
        state.geoService.findCountryByIp(genRandomIpAddress());
    }

    private String genRandomIpAddress() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int i = r.nextInt(0, 256);
        return "195.140.160." + i;
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(".*" + GeoServiceBenchmark.class.getSimpleName() + ".*")
                .warmupIterations(0)
                .measurementIterations(7)
                .forks(1)
                .build();

        new Runner(options).run();
    }

}
