package io.github.lyubent;

import io.github.lyubent.config.CassthreadingConfig;
import io.github.lyubent.exception.ConfigException;
import io.github.lyubent.exception.CommonMsgs;
import io.github.lyubent.thread.MultiSession;
import io.github.lyubent.thread.MultiThread;

public class MultiSessionBenchmark {

    // 480 for shared obj.
    public static void main(String[] args)  {
        if (args.length < 2)
            throw new ConfigException(CommonMsgs.tooFewArgs());

        String[] contactPoints = new String[args.length-1];
        for (int i=1; i<args.length; i++)
            contactPoints[i-1] = args[i].trim();


        final CassthreadingConfig conf = CassthreadingConfig.getInstance();

        // todo - needs better error handling
        // todo - this 'func' doesn't belong in main.
        String arg1 = args[0];
        if (arg1.trim().toLowerCase().equals("thread")) {
            new MultiThread(contactPoints, conf.getParallelism()).runBenchmark();
        } else if (arg1.trim().toLowerCase().equals("session")) {
            new MultiSession(contactPoints, conf.getParallelism()).runBenchmark();
        } else {
            throw new ConfigException(CommonMsgs.invalidArg1(arg1));
        }
    }
}
