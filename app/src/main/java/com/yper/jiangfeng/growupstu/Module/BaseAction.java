package com.yper.jiangfeng.growupstu.Module;

import java.util.UUID;

/**
 * Created by Feng on 2016/7/12.
 */
public class BaseAction {

    //基本行为，行为分为，带分数的，不带分数的，各种，基本行为没有分值
    private UUID _id= UUID.randomUUID();
    private String actionName;
    private String actionType;

    public BaseAction(String actionName, String actionType) {
        this.actionName = actionName;
        this.actionType = actionType;

    }

    public UUID get_id() {
        return _id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

}
