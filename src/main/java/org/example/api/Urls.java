package org.example.api;

import org.example.config.ConfigProvider;

public class Urls {
    public static final String BASE_URI = ConfigProvider.CONFIG.apiUrl();
    public static final String GOODS_LIST = "/goods/list";
    public static final String GOODS_ADD = "/goods/add";
    public static final String GOODS_BY_ID = "/goods/{id}";
}
