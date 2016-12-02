package jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Created by Maksym_Bondarenko on 12/2/2016.
 */
public class Sample {

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(Sample.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();

    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void bigDec () {
        new BigDecimal("0.005").setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void doubleVal() {

        Math.round(new Double("0.005"));
    }

}
