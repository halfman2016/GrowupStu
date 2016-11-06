package com.yper.jiangfeng.growupstu.Module;

import java.util.UUID;

/**
 * Created by jiangfeng on 2016/11/1.
 */

public class Comment {

    private UUID commentpeopleid;
    private String commentbody;
    private String commentpeoplename;

    public Comment(UUID commentpeopleid,String commentpeoplename, String commentbody) {
        this.commentpeopleid = commentpeopleid;
        this.commentbody = commentbody;
        this.commentpeoplename=commentpeoplename;
    }

    public UUID getCommentpeopleid() {
        return commentpeopleid;
    }

    public void setCommentpeopleid(UUID commentpeopleid) {
        this.commentpeopleid = commentpeopleid;
    }

    public String getCommentbody() {
        return commentbody;
    }

    public void setCommentbody(String commentbody) {
        this.commentbody = commentbody;
    }

    public String getCommentpeoplename() {
        return commentpeoplename;
    }

    public void setCommentpeoplename(String commentpeoplename) {
        this.commentpeoplename = commentpeoplename;
    }
}
