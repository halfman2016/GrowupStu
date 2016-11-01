package com.yper.jiangfeng.growupstu.Module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Feng on 2016/6/16.
 */
public class ScoreAction extends BaseAction {
    //带分数的行为，是基本行为的子类

    private String checkedTeaName; //检查老师姓名
    private UUID checkedTeaid;  //检查教师ID
    private ArrayList<UUID> relativeStuIds;
    private ArrayList<String> relativeStuNames;  //相关学生名字集合
    private List<Student> relativeStus;

    private int actionValue;     //行为分值，为正加分，为负减分 //默认分值，通过actionName查map出来
    private Date actionPinTime;  //行为记录时间
    private Date actionOccurTime;  //行为发生时间

    public List<Student> getRelativeStus() {
        return relativeStus;
    }

    public void setRelativeStus(List<Student> relativeStus) {
        this.relativeStus = relativeStus;
    }

    //最小构造函数，必须有分值
    public ScoreAction(String actionName, String actionType, int actionScoreValue) {
        super(actionName, actionType);
    }


    //常用构造函数，带分值,发生时间，默认pin时间今日

    public ScoreAction(String actionName, String actionType, int actionValue, Date actionOccurTime) {
        super(actionName, actionType);
        this.actionPinTime = new Date();
        this.actionOccurTime = actionOccurTime;
        this.actionValue = actionValue;

    }

    //常用构造函数，带教师姓名


    public String getCheckedTeaName() {
        return checkedTeaName;
    }

    public void setCheckedTeaName(String checkedTeaName) {
        this.checkedTeaName = checkedTeaName;
    }

    public UUID getCheckedTeaid() {
        return checkedTeaid;
    }

    public void setCheckedTeaid(UUID checkedTeaid) {
        this.checkedTeaid = checkedTeaid;
    }

    public ArrayList<UUID> getRelativeStuIds() {
        return relativeStuIds;
    }

    public void setRelativeStuIds(ArrayList<UUID> relativeStuIds) {
        this.relativeStuIds = relativeStuIds;
    }

    public ArrayList<String> getRelativeStuNames() {
        return relativeStuNames;
    }

    public void setRelativeStuNames(ArrayList<String> relativeStuNames) {
        this.relativeStuNames = relativeStuNames;
    }

    public int getActionValue() {
        return actionValue;
    }

    public void setActionValue(int actionValue) {
        this.actionValue = actionValue;
    }

    public Date getActionPinTime() {
        return actionPinTime;
    }

    public void setActionPinTime(Date actionPinTime) {
        this.actionPinTime = actionPinTime;
    }

    public Date getActionOccurTime() {
        return actionOccurTime;
    }

    public void setActionOccurTime(Date actionOccurTime) {
        this.actionOccurTime = actionOccurTime;
    }
}
