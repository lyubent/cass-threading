package io.github.lyubent.thread;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import java.util.concurrent.CountDownLatch;

public class MultiThreadRunnable implements Runnable {

    private Session session;
    private PreparedStatement prepared;
    private Integer threadId;
    private CountDownLatch latch;

    public MultiThreadRunnable(Session session, PreparedStatement prepared, Integer threadId, CountDownLatch latch) {
        this.session = session;
        this.prepared = prepared;
        this.threadId = threadId;
        this.latch = latch;
    }

    public void run() {
        for (int i = 0; i < 1000; i++)
            session.execute(prepared.bind(i+threadId, "text", i*i));
        latch.countDown();
    }

}
