package com.yper.jiangfeng.growupstu.Module;

import java.util.UUID;

/**
 * Created by jiangfeng on 2016/11/1.
 */

public class Zan {

    private UUID zanpeopleid;
    private String zanpeoplename;

    public Zan(UUID zanpeopleid, String zanpeoplename) {
        this.zanpeopleid = zanpeopleid;
        this.zanpeoplename = zanpeoplename;
    }

    public UUID getZanpeopleid() {
        return zanpeopleid;
    }

    public void setZanpeopleid(UUID zanpeopleid) {
        this.zanpeopleid = zanpeopleid;
    }

    public String getZanpeoplename() {
        return zanpeoplename;
    }

    public void setZanpeoplename(String zanpeoplename) {
        this.zanpeoplename = zanpeoplename;
    }
}
