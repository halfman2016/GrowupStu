package com.yper.jiangfeng.growupstu.Module;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Feng on 2016/6/21.
 */
public class Photopic extends Photo{
    private Date photodate; //拍照时间
    private UUID photoauthorid; //拍照的人ID
    private String photoauthor;
    private String photomemo;

    private UUID belongToSubject; // 照片属于哪个专题
    private int haspined; //0从未标定，1 标定了，n 标定了几次


    public Date getPhotodate() {
        return photodate;
    }

    public void setPhotodate(Date photodate) {
        this.photodate = photodate;
    }

    public UUID getPhotoauthorid() {
        return photoauthorid;
    }

    public void setPhotoauthorid(UUID photoauthorid) {
        this.photoauthorid = photoauthorid;
    }

    public String getPhotoauthor() {
        return photoauthor;
    }

    public void setPhotoauthor(String photoauthor) {
        this.photoauthor = photoauthor;
    }

    public String getPhotomemo() {
        return photomemo;
    }

    public void setPhotomemo(String photomemo) {
        this.photomemo = photomemo;
    }


    public UUID getBelongToSubject() {
        return belongToSubject;
    }

    public void setBelongToSubject(UUID belongToSubject) {
        this.belongToSubject = belongToSubject;
    }

    public int getHaspined() {
        return haspined;
    }

    public void setHaspined(int haspined) {
        this.haspined = haspined;
    }
}
