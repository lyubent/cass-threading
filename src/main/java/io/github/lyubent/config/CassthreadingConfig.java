package io.github.lyubent.config;

public class CassthreadingConfig {

    private static CassthreadingConfig instance = null;

    private CassthreadingConfig(){};

    public static CassthreadingConfig getInstance()
    {
        if (instance == null)
            instance = YAMLConfigParser.parseCfg();

        return instance;
    }

    private Integer parallelism;
    private String[] nodes;

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public String[] getNodes() {
        return nodes;
    }

    public void setNodes(String[] nodes) {
        this.nodes = nodes;
    }

    private String benchmark;

    public Integer getParallelism() {
        return parallelism;
    }

    public void setParallelism(Integer parallelism) {
        this.parallelism = parallelism;
    }
}
