package com.yper.jiangfeng.growupstu.Module;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Feng on 2016/6/16.
 */
public class Student extends People {
    private String rankname; //级别，称号
    private int score;  //分数
    private String gradeclass;
    private UUID gradeclassid;
    ArrayList<UUID> daycommonActionids = new ArrayList<>(); //记录日检查的内容
    ArrayList<UUID> picPinActions=new ArrayList<>();  //记录标定的内容
    private String QQ;

    public Student(String name) {
        super(name);
    }


    public String getRankname() {
        return rankname;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGradeclass() {
        return gradeclass;
    }

    public void setGradeclass(String gradeclass) {
        this.gradeclass = gradeclass;
    }

    public UUID getGradeclassid() {
        return gradeclassid;
    }

    public void setGradeclassid(UUID gradeclassid) {
        this.gradeclassid = gradeclassid;
    }

    public ArrayList<UUID> getDaycommonActionids() {
        return daycommonActionids;
    }

    public void setDaycommonActionids(ArrayList<UUID> scoreActionsUUID) {
        this.daycommonActionids = scoreActionsUUID;
    }

    public ArrayList<UUID> getPicPinActions() {
        return picPinActions;
    }

    public void setPicPinActions(ArrayList<UUID> picPinActions) {
        this.picPinActions = picPinActions;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }
}
