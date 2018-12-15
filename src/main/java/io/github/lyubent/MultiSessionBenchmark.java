package io.github.lyubent;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import io.github.lyubent.client.ClientBuilder;
import io.github.lyubent.exception.ConfigException;
import io.github.lyubent.exception.CommonMsgs;
import io.github.lyubent.query.Query;
import io.github.lyubent.thread.SharedSessionBased;
import io.github.lyubent.thread.SessionPerThreadBased;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSessionBenchmark {

    private static final int CLIENT_COUNT = 480;

    // 480 for shared obj.
    public static void main(String[] args)  {

        if (args.length < 2)
            throw new ConfigException(CommonMsgs.tooFewArgs());

        String[] contactPoints = new String[args.length-1];
        for (int i=1; i<args.length; i++) {
            contactPoints[i-1] = args[i].trim();
        }

        ClientBuilder cb = new ClientBuilder(contactPoints);
        MultiSessionBenchmark s = new MultiSessionBenchmark();

        // todo - needs better error handling
        // todo - this 'func' doesn't belong in main.
        String arg1 = args[0];
        if (arg1.trim().toLowerCase().equals("thread")) {
            s.multiThread(cb);
        } else if (arg1.trim().toLowerCase().equals("session")) {
            s.multiSession(contactPoints);
        } else {
            throw new ConfigException(CommonMsgs.invalidArg1(arg1));
        }
    }


    // creates n threads containing 1 shared cluster and session object across all threads.
    public  void multiThread(ClientBuilder cb) {
        // single shared client.
        Cluster cluster = cb.getCluster();
        Session ses = cluster.connect();
        // run some prep for required KS / CF.
        ses.execute(Query.getKeyspaceDefThread());
        ses.execute(Query.getTblDefThread());
        // share prepared statement.
        PreparedStatement prepared = ses.prepare(Query.getPreparedInsertThread());

        CountDownLatch latch = new CountDownLatch(CLIENT_COUNT);
        ExecutorService executor= Executors.newFixedThreadPool(CLIENT_COUNT);

        try{
            for (int i = 1; i < CLIENT_COUNT +1; i++){
                executor.execute(new SharedSessionBased(ses, prepared, i*10000, latch));
            }

            latch.await();

        }catch(InterruptedException ie){
            ie.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    // Creates n threads containing 1 cluster and session object each.
    public void multiSession(String[] contactPoints) {
        CountDownLatch latch = new CountDownLatch(CLIENT_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(CLIENT_COUNT);
        try{
            for (int i = 1; i < CLIENT_COUNT +1; i++){
                executor.execute(new SessionPerThreadBased(i*10000, latch, contactPoints));
            }
            latch.await();
        }catch(Exception err){
            err.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
