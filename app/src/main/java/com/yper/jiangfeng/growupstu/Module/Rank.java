package com.yper.jiangfeng.growupstu.Module;

/**
 * Created by Feng on 2016/6/16.
 */
public class Rank extends Object {

    //分数到了自动升级？还是手动升级
    //自动升级，每个rank对象,app一开始就读入rank对象，目前7个rank对象
    //读取全部rank，进入一个list，对每个rank进行遍历，从而判断一个分数应该是什么级别
    //然后级别信息写入学生信息，每次加分后进行一个级别遍历，获得最新的rank信息。

    //一旦学校彻底修改rank设计，只要学生有一次分数行为，就会获得最新，这个是很好的设计

    private String rankName;
    private int doorScore;  //本级门槛分数
    private int highestscore; //本级最高分数，+1升级
    private int ranklevel; //0，准学生，正式入校

    public Rank() {
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getDoorScore() {
        return doorScore;
    }

    public void setDoorScore(int doorScore) {
        this.doorScore = doorScore;
    }

    public int getHighestscore() {
        return highestscore;
    }

    public void setHighestscore(int highestscore) {
        this.highestscore = highestscore;
    }

    public int getRanklevel() {
        return ranklevel;
    }

    public void setRanklevel(int ranklevel) {
        this.ranklevel = ranklevel;
    }
}

