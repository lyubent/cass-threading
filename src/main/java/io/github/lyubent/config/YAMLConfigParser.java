package io.github.lyubent.config;

import io.github.lyubent.exception.ConfigException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

public class YAMLConfigParser {

    /**
     * Parses configuration file.
     * Assumes cassthrd.yaml is located in root folder of project.
     * Purposefully package level to prevent misuse.
     */
    static CassthreadingConfig parseCfg() {
        // todo Test this extensively. Relying on paths to the cfg can cause issues.
        String cfgPath = "src/main/resources/cassthrd.yaml";
        try (InputStream inputStream = new FileInputStream(cfgPath)){
            Yaml yaml = new Yaml();
            return yaml.loadAs(inputStream, CassthreadingConfig.class);
        } catch (IOException ioex) {
            throw new ConfigException("Configuration file cassthrd.yaml not found.");
        }
    }
}
