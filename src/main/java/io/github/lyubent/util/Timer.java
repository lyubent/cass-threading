package io.github.lyubent.util;

import java.time.Instant;

public class Timer {

    private Instant start = now();

    public void displayDuration() {
        System.out.printf("Benchmark executed for %d sec.", now().getEpochSecond() - start.getEpochSecond());
    }
    // Wrapper for now.
    private Instant now() {
        return Instant.now();
    }
}
