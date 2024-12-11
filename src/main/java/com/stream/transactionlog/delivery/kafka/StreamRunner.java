package com.stream.transactionlog.delivery.kafka;
import com.stream.transactionlog.infrastructure.config.AppConfig;
import com.stream.transactionlog.infrastructure.config.ConfigLoader;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.stream.transactionlog.usecase.EnrichOrderUseCase;

public class StreamRunner {

    public static void start() {
        ConfigLoader cfgLoader = ConfigLoader.getInstance();
        AppConfig appConfig = cfgLoader.getAppConfig();

        String bootstrapServers = appConfig.getKafka().getBootstrapServers();
        List<AppConfig.UseCaseConfig> activeUseCases = cfgLoader.getActiveUseCases();
        for (AppConfig.UseCaseConfig usecaseCfg : activeUseCases) {
            String className = usecaseCfg.getClassName();
            try {
                Class<?> clazz = Class.forName(className);
                Object useCase = clazz.getDeclaredConstructor().newInstance();
                switch (className) {
                    case "com.stream.transactionlog.usecase.EnrichOrderUseCase":
                        ((EnrichOrderUseCase) useCase).run(usecaseCfg,bootstrapServers);
                        break;
                    case "com.stream.transactionlog.usecase.EnrichCustomerUseCase":
                        System.out.println("Not implement EnrichCustomerUseCase");
                        break;
                    default:
                        System.out.println("Unknown use case: " + className);
                        break;
                }

            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + className);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                System.err.println("InstantiationException | IllegalAccessException | NoSuchMethodException:" + e.getMessage());
            } catch (InvocationTargetException e) {
                System.err.println("InvocationTargetException: " + e.getMessage());
            }
        }
    }
}

