package io.github.lyubent.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSession implements Benchmark {

    private String[] contactPoints;
    private int clientCount;

    /**
     * @param contactPoints - Nodes to connect to for benchmarking
     * @param clientCount - Number of sessions to use for parallel execution
     */
    public MultiSession(String[] contactPoints, int clientCount) {
        this.contactPoints = contactPoints;
        this.clientCount = clientCount;
    }

    /**
     * Creates n threads containing 1 cluster and session object each.
     */
    public void runBenchmark() {
        CountDownLatch latch = new CountDownLatch(clientCount);
        ExecutorService executor = Executors.newFixedThreadPool(clientCount);
        try {
            for (int i = 1; i < clientCount +1; i++)
                executor.execute(new MultiSessionRunnable(i*10000, latch, contactPoints));
            latch.await();
        }catch(Exception err){
            err.printStackTrace();
        }
    }
}
