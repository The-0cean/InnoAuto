package org.example.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProvider {

    public static final ProjectConfig CONFIG = ConfigFactory.create(ProjectConfig.class);

    static {
        System.out.println("========== Test configuration ==========");
        System.out.println("base.url         = " + CONFIG.baseUrl());
        System.out.println("api.url          = " + CONFIG.apiUrl());
        System.out.println("timeout          = " + CONFIG.timeout() + " ms");
        System.out.println("log.level        = " + CONFIG.logLevel());
        System.out.println("start.good.name  = " + CONFIG.startGoodName());
        System.out.println("start.good.price = " + CONFIG.startGoodPrice());
        System.out.println("========================================");
    }

    private ConfigProvider() {
    }
}
