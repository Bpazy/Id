# Id
![Maven](https://img.shields.io/maven-central/v/com.github.bpazy/id.svg)
[![Build Status](https://travis-ci.com/Bpazy/Id.svg?branch=master)](https://travis-ci.com/Bpazy/Id)
[![codecov](https://codecov.io/gh/Bpazy/Id/branch/master/graph/badge.svg)](https://codecov.io/gh/Bpazy/Id)

分布式id生成，整体随时间递增。

最大支持1024个节点，每个节点每毫秒生成ID上限为4096个。

## Usage
```java
public class Main {
    public static void main(String[] args) {
        System.out.println(Id.next());
    }
}
```

### Benchmark
```
# JMH version: 1.19
# VM version: JDK 1.8.0_161, VM 25.161-b12
# VM invoker: C:\Program Files\Java\jdk1.8.0_161\jre\bin\java.exe
# VM options: -javaagent:D:\Program Files\JetBrains\ToolBox\apps\IDEA-U\ch-0\183.4588.61\lib\idea_rt.jar=4143:D:\Program Files\JetBrains\ToolBox\apps\IDEA-U\ch-0\183.4588.61\bin -Dfile.encoding=UTF-8
# Warmup: 1 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: com.github.bpazy.idbenchmark.Main.idBenchmark

# Run progress: 0.00% complete, ETA 00:00:18
# Fork: 1 of 3
# Warmup Iteration   1: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
2024509.850 ops/s
Iteration   1: 4096581.066 ops/s
Iteration   2: 4097063.699 ops/s
Iteration   3: 4097580.963 ops/s
Iteration   4: 4099701.559 ops/s
Iteration   5: 4098296.272 ops/s

# Run progress: 33.33% complete, ETA 00:00:13
# Fork: 2 of 3
# Warmup Iteration   1: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
2328552.612 ops/s
Iteration   1: 4079420.331 ops/s
Iteration   2: 4023722.559 ops/s
Iteration   3: 4098380.166 ops/s
Iteration   4: 4101047.384 ops/s
Iteration   5: 4097627.770 ops/s

# Run progress: 66.67% complete, ETA 00:00:06
# Fork: 3 of 3
# Warmup Iteration   1: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
2343553.817 ops/s
Iteration   1: 4096149.582 ops/s
Iteration   2: 4096496.911 ops/s
Iteration   3: 4069862.862 ops/s
Iteration   4: 4090216.938 ops/s
Iteration   5: 4069581.830 ops/s


Result "com.github.bpazy.idbenchmark.Main.idBenchmark":
  4087448.659 ±(99.9%) 21897.203 ops/s [Average]
  (min, avg, max) = (4023722.559, 4087448.659, 4101047.384), stdev = 20482.657
  CI (99.9%): [4065551.457, 4109345.862] (assumes normal distribution)


# Run complete. Total time: 00:00:20

Benchmark          Mode  Cnt        Score       Error  Units
Main.idBenchmark  thrpt   15  4087448.659 ± 21897.203  ops/s
```

reference by [https://segmentfault.com/a/1190000011282426](https://segmentfault.com/a/1190000011282426)
