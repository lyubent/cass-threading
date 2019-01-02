package io.github.lyubent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class Timer {

    private Instant start = now();
    private final static Logger logger = LoggerFactory.getLogger(Timer.class);

    public void displayDuration() {
        logger.info("Benchmark executed for %d sec.", now().getEpochSecond() - start.getEpochSecond());
    }
    // Wrapper for now.
    private Instant now() {
        return Instant.now();
    }
}
