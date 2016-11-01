package com.yper.jiangfeng.growupstu.Module;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jiangfeng on 2016/10/5.
 */

public class Annouce {

    private String Annouceauthor;
    private String AnnouceBody;
    private Date AnnouceTime;
    private UUID _id= UUID.randomUUID();
    private UUID annouceauthorid;
    private UUID belongtoSubject;

    public Annouce(String annouceauthor, String annouceBody, Date annouceTime) {
        Annouceauthor = annouceauthor;
        AnnouceBody = annouceBody;
        AnnouceTime = annouceTime;
    }

    public UUID getBelongtoSubject() {
        return belongtoSubject;
    }

    public void setBelongtoSubject(UUID belongtoSubject) {
        this.belongtoSubject = belongtoSubject;
    }

    public String getAnnouceauthor() {
        return Annouceauthor;
    }

    public void setAnnouceauthor(String annouceauthor) {
        Annouceauthor = annouceauthor;
    }

    public String getAnnouceBody() {
        return AnnouceBody;
    }

    public void setAnnouceBody(String annouceBody) {
        AnnouceBody = annouceBody;
    }

    public Date getAnnouceTime() {
        return AnnouceTime;
    }

    public void setAnnouceTime(Date annouceTime) {
        AnnouceTime = annouceTime;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public UUID getAnnouceauthorid() {
        return annouceauthorid;
    }

    public void setAnnouceauthorid(UUID annouceauthorid) {
        this.annouceauthorid = annouceauthorid;
    }
}
