package io.github.lyubent;

import io.github.lyubent.config.CassthreadingConfig;
import io.github.lyubent.exception.ConfigException;
import io.github.lyubent.exception.CommonMsgs;
import io.github.lyubent.thread.MultiSession;
import io.github.lyubent.thread.MultiThread;

public class MultiSessionBenchmark {

    // 480 for shared obj.
    public static void main(String[] args)  {

        final CassthreadingConfig conf = CassthreadingConfig.getInstance();

        switch (conf.getBenchmark().trim()) {
            case "thread":
                new MultiThread(conf.getNodes(), conf.getParallelism()).runBenchmark();
                break;
            case "session":
                new MultiSession(conf.getNodes(), conf.getParallelism()).runBenchmark();
                break;
            default:
                throw new ConfigException(CommonMsgs.missconfiguredYaml("benchmark", "'thread' or 'session'"));
        }
    }
}
