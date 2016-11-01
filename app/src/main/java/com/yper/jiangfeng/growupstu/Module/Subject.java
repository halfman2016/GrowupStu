package com.yper.jiangfeng.growupstu.Module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Feng on 2016/9/20.
 */

public class Subject  {
    private String subjectName ;
    private String SubjectInfo;
    private Date startTime;
    private Date endTime;
    private UUID _id= UUID.randomUUID();
    private String Tid;
    private String teacherName;
    private List<Student> students=new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectInfo() {
        return SubjectInfo;
    }

    public void setSubjectInfo(String subjectInfo) {
        SubjectInfo = subjectInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public UUID get_id() {
        return _id;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
