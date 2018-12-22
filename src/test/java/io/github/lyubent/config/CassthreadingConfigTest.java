package io.github.lyubent.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CassthreadingConfigTest {
    @Test
    public void evaluatesExpression() {
        CassthreadingConfig conf = CassthreadingConfig.getInstance();
        assertEquals(conf.getParallelism(), new Integer(480));
    }
}
