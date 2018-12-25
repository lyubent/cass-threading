package io.github.lyubent;

import io.github.lyubent.config.CassthreadingConfig;
import io.github.lyubent.exception.ConfigException;
import io.github.lyubent.exception.CommonMsgs;
import io.github.lyubent.benchmark.MultiSession;
import io.github.lyubent.benchmark.MultiThread;
import io.github.lyubent.util.Timer;

public class MultiSessionBenchmark {

    public static void main(String[] args)  {

        final CassthreadingConfig conf = CassthreadingConfig.getInstance();
        Timer t = new Timer();

        switch (conf.getBenchmark().trim()) {
            case "thread":
                new MultiThread(conf.getNodes(), conf.getParallelism(), conf.getPort()).runBenchmark();
                break;
            case "session":
                new MultiSession(conf.getNodes(), conf.getParallelism(), conf.getPort()).runBenchmark();
                break;
            default:
                throw new ConfigException(CommonMsgs.missconfiguredYaml("benchmark", "'thread' or 'session'"));
        }

        // display benchmark duration from the JVM POV.
        t.displayDuration();
    }
}
