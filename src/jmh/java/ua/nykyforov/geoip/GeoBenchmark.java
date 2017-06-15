package ua.nykyforov.geoip;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ua.nykyforov.geoip.dbip.api.DbIpClient;
import ua.nykyforov.geoip.maxmind.MaxMindGeoService;

import java.util.concurrent.ThreadLocalRandom;

import static ua.nykyforov.util.IOUtils.getResourceFile;

/**
 * @author <a href="mailto:s.nikiforov@corp.nekki.ru">Sergey Nikiforov</a>
 */
@Fork(1)
@BenchmarkMode({Mode.Throughput})
public class GeoBenchmark {

    @State(Scope.Benchmark)
    public static class DbIpState {
        DbIpClient client;

        @Setup
        public void setup() {
            client = new DbIpClient(getResourceFile(Global.DB_IP_DATABASE_NAME));
        }
    }

    @State(Scope.Benchmark)
    public static class MaxMindState {
        MaxMindGeoService service;

        @Setup
        public void setup() {
            service = MaxMindGeoService.fromResource(Global.MAX_MIND_DATABASE_NAME);
        }
    }

    @Benchmark
    @Measurement(iterations = 5)
    @Warmup(iterations = 2)
    public void maxMind(MaxMindState state) {
        state.service.findCountryByIp(genRandomIpAddress());
    }

    @Benchmark
    @Measurement(iterations = 5)
    @Warmup(iterations = 2)
    public void dbIp(DbIpState state) {
        state.client.lookup(genRandomIpAddress());
    }

    private String genRandomIpAddress() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int i = r.nextInt(0, 256);
        return "195.140.160." + i;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(".*" + GeoBenchmark.class.getSimpleName() + ".*")
                .build();

        new Runner(options).run();
    }

}
