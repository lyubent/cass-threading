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

    public Integer getParallelism() {
        return parallelism;
    }

    public void setParallelism(Integer parallelism) {
        this.parallelism = parallelism;
    }
}
