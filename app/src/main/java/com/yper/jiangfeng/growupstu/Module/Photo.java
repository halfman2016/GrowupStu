package com.yper.jiangfeng.growupstu.Module;

import java.util.UUID;

/**
 * Created by jiangfeng on 2016/10/10.
 */

public class Photo {

    private UUID _id= UUID.randomUUID();
    private byte[] photofile;
    private byte[] photopreview;
    private String picname;


    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public UUID get_id() {
        return _id;
    }


    public byte[] getPhotofile() {
        return photofile;
    }

    public void setPhotofile(byte[] photofile) {
        this.photofile = photofile;
    }

    public byte[] getPhotopreview() {
        return photopreview;
    }

    public void setPhotopreview(byte[] photopreview) {
        this.photopreview = photopreview;
    }
}
