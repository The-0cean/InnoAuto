package org.example.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config.properties"
})
public interface ProjectConfig extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("api.url")
    String apiUrl();

    @Key("timeout")
    long timeout();

    @Key("log.level")
    String logLevel();

    @Key("admin.login")
    String adminLogin();

    @Key("admin.password")
    String adminPassword();

    @Key("start.good.name")
    String startGoodName();

    @Key("start.good.price")
    int startGoodPrice();
}
