package com.yper.jiangfeng.growupstu.Module;

/**
 * Created by Feng on 2016/7/27.
 * 班级对象，每个班级对象有自己的生命周期，类似分组。
 * 一般的，一个学年一个生命周期。
 * 设定 isActive 为班级有效
 * 检查endtime也可以设定isActive
 *
 */
public class GradeClass extends TeamBase {

    public GradeClass(String name) {
        super(name);

    }
}
