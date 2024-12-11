package com.stream.transactionlog.infrastructure.config;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigLoader {
    private static ConfigLoader instance;
    private AppConfig appConfig;
    private ConfigLoader() {
        loadConfig();
    }
    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    private void loadConfig() {
        String configEnv = System.getenv("RUN_ENV");
        if (configEnv == null || configEnv.isEmpty()) {
            configEnv = "local";
        }
        String configFileName = String.format("config/config.%s.yaml", configEnv);

        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (inputStream == null) {
                throw new RuntimeException("application.yaml not found in the resources folder");
            }
            Yaml yaml = new Yaml();
            appConfig = yaml.loadAs(inputStream, AppConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public List<AppConfig.UseCaseConfig> getActiveUseCases() {
        return appConfig.getUsecase().stream()
                .filter(AppConfig.UseCaseConfig::isActive)
                .collect(Collectors.toList());
    }

}
