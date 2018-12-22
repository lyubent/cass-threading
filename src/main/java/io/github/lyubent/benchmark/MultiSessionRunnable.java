package io.github.lyubent.benchmark;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import io.github.lyubent.query.Query;

import java.util.concurrent.CountDownLatch;

public class MultiSessionRunnable implements Runnable {

    Integer threadId;
    Session ses;
    CountDownLatch latch;

    public MultiSessionRunnable(Integer threadId, CountDownLatch latch, String[] contactPoints, int port) {

        this.threadId = threadId;
        this.latch = latch;
        // build client per thread.
        DCAwareRoundRobinPolicy dcAwareRoundRobinPolicy = DCAwareRoundRobinPolicy.builder()
                .withLocalDc(Query.getLocalDC())
                .withUsedHostsPerRemoteDc(1)
                .build();
        Cluster cluster = Cluster.builder().withLoadBalancingPolicy(new TokenAwarePolicy(dcAwareRoundRobinPolicy))
                .addContactPoints(contactPoints)
                .withPort(port)
                .build();
        ses = cluster.connect();
        ses.execute(Query.getKeyspaceDefSession());
        ses.execute(Query.getTblDefSession());
    }


    public void run() {
        PreparedStatement prepared = ses.prepare(Query.getPreparedInstertSession());
        for (int i = 0; i < 1000; i++) {
            ses.execute(prepared.bind(i+threadId, "text", i*i));
        }
        latch.countDown();
    }


}
