package com.yper.jiangfeng.growupstu.Module;

import java.util.Date;

/**
 * Created by Feng on 2016/7/11.
 */
public class BaseInfoItem {
    //基本信息显示项目，item不会无缘无故在list中显示，所以infoItem一定会包含一个BaseAction对象
    //这里的属性，主要是作为一个显示项目，应该具备的内容
    //数据域
    //排序
    //布局筛选

    private String infoTitle;  //信息标题 显示在第一行的
    private Date infoTime;   //信息时间   显示在第一行行尾巴
    private String infotype;  //日常规  专题 双周排名…… 显示在第一行
    private BaseAction actionObject;  //通知的行为实体
    private int order; //0 排序参数

    public BaseInfoItem(BaseAction actionObject) {
        this.infoTitle = actionObject.getActionName();
        this.infotype = actionObject.getActionType();
        this.actionObject = actionObject;
        this.order = 50;
        this.infoTime = new Date();
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public Date getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(Date infoTime) {
        this.infoTime = infoTime;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public Object getActionObject() {
        return actionObject;
    }

    public void setActionObject(BaseAction actionObject) {
        this.actionObject = actionObject;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
