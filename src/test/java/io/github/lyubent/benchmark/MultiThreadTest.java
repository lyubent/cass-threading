package io.github.lyubent.benchmark;

import com.datastax.driver.core.Session;
import com.github.nosan.embedded.cassandra.Config;
import com.github.nosan.embedded.cassandra.ExecutableConfig;
import com.github.nosan.embedded.cassandra.junit.CassandraRule;
import io.github.lyubent.query.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultiThreadTest {

    private static final int PARALLELISM = 3;
    private static final String[] contactPoints = {"127.0.0.1"};
    private Session session;

    @ClassRule
    public static final CassandraRule cassandra = new CassandraRule(getRuntimeConfig());

    @Before
    public void setUp() {
        session = cassandra.getSession();
        // create KSs and CFs
        session.execute(Query.getKeyspaceDefThread());
        session.execute(Query.getTblDefThread());
    }

    @After
    public void tearDown() {
        session.execute(Query.getKeyspaceDefThread());
    }

    @Test
    public void testBenchmark() {

        new MultiThread(contactPoints, 9042, PARALLELISM).runBenchmark();
        Long threadRowCount = session.execute("SELECT count(*) FROM multithread.tbl").one().getLong(0);
        assertEquals(1000 * PARALLELISM, threadRowCount.longValue());
    }

    /**
     * Allows for modifying config of embedded C*
     *
     * @return ExecutableConfig - embedded C* configuration
     */
    private static ExecutableConfig getRuntimeConfig() {
        ExecutableConfig conf = new ExecutableConfig();
        Config cfg = conf.getConfig();
        // set native transport port to 9042
        cfg.setNativeTransportPort(9042);
        conf.setConfig(cfg);

        return conf;
    }
}
