package com.stream.transactionlog.infrastructure.config;

import java.util.List;
import java.util.Map;

public class AppConfig {
    private ApplicationConfig application; // Thay đổi này để phản ánh cấu trúc YAML
    private KafkaConfig kafka;
    private List<UseCaseConfig> usecase;

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public KafkaConfig getKafka() {
        return kafka;
    }

    public void setKafka(KafkaConfig kafka) {
        this.kafka = kafka;
    }

    public List<UseCaseConfig> getUsecase() {
        return usecase;
    }

    public void setUsecase(List<UseCaseConfig> usecase) {
        this.usecase = usecase;
    }

    public static class ApplicationConfig {
        private String version;
        private String mode;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }

    public static class KafkaConfig {
        private String bootstrapServers;

        public String getBootstrapServers() {
            return bootstrapServers;
        }

        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }
    }

    public static class UseCaseConfig {
        private String className;
        private boolean active;
        private String applicationID;
        private int commitTime;
        private Map<String, String> topics;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getApplicationID() {
            return applicationID;
        }

        public void setApplicationID(String applicationID) {
            this.applicationID = applicationID;
        }

        public int getCommitTime() {
            return commitTime;
        }

        public void setCommitTime(int commitTime) {
            this.commitTime = commitTime;
        }

        public Map<String, String> getTopics() {
            return topics;
        }

        public void setTopics(Map<String, String> topics) {
            this.topics = topics;
        }
    }
}