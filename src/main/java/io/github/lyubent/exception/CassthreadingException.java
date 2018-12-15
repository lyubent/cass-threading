package io.github.lyubent.exception;

public abstract class CassthreadingException extends RuntimeException {

    private final boolean fatal;

    public CassthreadingException(String msg, boolean fatal) {
        this.fatal = fatal;
    }

    public CassthreadingException(String msg) {
        fatal = true;
    }

    public CassthreadingException() {
        super("Unknown exception");
        fatal = true;
    }

    public boolean isFatal() {
        return fatal;
    }
}
