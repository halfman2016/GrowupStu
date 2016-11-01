package com.yper.jiangfeng.growupstu.Module;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Feng on 2016/7/12.
 */
public class WeekReport extends BaseAction {

    private Map<Integer, String> rank = new HashMap<Integer, String>();

    public WeekReport(String reportTitle, HashMap rank) {
        super(reportTitle, "双周报告");
        this.rank = rank;

    }


    public Map<Integer, String> getRank() {
        return rank;
    }

    public void setRank(Map<Integer, String> rank) {
        this.rank = rank;
    }
}
