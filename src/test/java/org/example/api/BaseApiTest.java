package org.example.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseApiTest {

    protected static final int NON_EXISTENT_ID = -1;


    @BeforeEach
    public void clearBefore() {
        GoodsApi.deleteAllGoods();
    }

    @AfterEach
    public void clearAfter() {
        GoodsApi.deleteAllGoods();
    }
}