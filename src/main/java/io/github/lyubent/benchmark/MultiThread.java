package io.github.lyubent.benchmark;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import io.github.lyubent.client.ClientBuilder;
import io.github.lyubent.query.Query;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread implements Benchmark {

    private String[] contactPoints;
    private int port;
    private int clientCount;

    /**
     * @param contactPoints - Nodes to connect to for benchmarking
     * @param clientCount - Number of runnables to execute in parallel
     */
    public MultiThread(String[] contactPoints, int port, int clientCount) {
        this.contactPoints = contactPoints;
        this.port = port;
        this.clientCount = clientCount;
    }

    /**
     * creates n threads containing 1 shared cluster and session object across all threads.
     */
    public  void runBenchmark() {

        ClientBuilder cb = new ClientBuilder(contactPoints, port);

        // single shared client.
        Cluster cluster = cb.getCluster();
        Session ses = cluster.connect();
        // run some prep for required KS / CF.
        ses.execute(Query.getKeyspaceDefThread());
        ses.execute(Query.getTblDefThread());
        // share prepared statement.
        PreparedStatement prepared = ses.prepare(Query.getPreparedInsertThread());

        CountDownLatch latch = new CountDownLatch(clientCount);
        ExecutorService executor= Executors.newFixedThreadPool(clientCount);

        try{
            for (int i = 1; i < clientCount +1; i++)
                executor.execute(new MultiThreadRunnable(ses, prepared, i*10000, latch));
            latch.await();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
